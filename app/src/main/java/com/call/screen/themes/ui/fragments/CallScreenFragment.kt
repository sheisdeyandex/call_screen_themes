package com.call.screen.themes.ui.fragments

import android.Manifest
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.bumptech.glide.Glide
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.data.database.AppDatabase
import com.call.screen.themes.data.model.AdapterModel
import com.call.screen.themes.data.model.DataModel
import com.call.screen.themes.data.model.MainModel
import com.call.screen.themes.data.model.VideoModel
import com.call.screen.themes.databinding.FragmentCallScreenBinding
import com.call.screen.themes.singleton.CallApplication
import com.call.screen.themes.viewmodels.CallScreenViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class CallScreenFragment : Fragment() {
    private val viewModel: CallScreenViewModel by viewModels()
    private var player: ExoPlayer? = null
    private var binding:FragmentCallScreenBinding? = null
    lateinit var adapterModel:AdapterModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCallScreenBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterModel = CallApplication.adapterModel
        initSharedPrefs()
        initPlayer()
        checkAppliedButton()
        initDb()
        showVideo()
        initClicks()
        initTexts()
        observeApply()
        binding?.ivProfile?.let { Glide.with(requireContext()).load(adapterModel.gender).into(it) }
    }
    private fun initTexts(){
        binding?.tvName?.text = adapterModel.name
        binding?.tvNumber?.text = "201-8747-5424"
    }
    private fun initPlayer(){
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding?.pVFullVideo?.player = exoPlayer
            }
    }
    private fun initSharedPrefs(){
        viewModel.sharedPrefs = activity?.getSharedPreferences(Constants.sharedPrefsContactsName,MODE_PRIVATE)?:return
    }
    private fun checkAppliedButton(){
        val isApplied = viewModel.isApplied(adapterModel._id)
        binding?.btnApply?.isEnabled = isApplied
        if (!isApplied){
            binding?.btnApply?.text = getString(R.string.applied)
        }
    }
    private fun observeApply(){
        CallApplication.applyTheme.observe(viewLifecycleOwner){
            if (it){
                CallApplication.applyTheme.postValue(false)
                binding?.btnApply?.isEnabled = false
                binding?.btnApply?.text = getString(R.string.applied)
                viewModel.storeToShow(MainModel(adapterModel._id,viewModel.videoUri.toString(),viewModel.contactsList),Constants.sharedPrefsContactsName)
            }
        }
    }
    private fun initClicks(){
        binding?.btnApply?.setOnClickListener {
            findNavController().navigate(R.id.action_callScreen_to_dialogApplyTheme)
        }
        binding?.btnContacts?.setOnClickListener {
            if (   ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.READ_CONTACTS
                    ),10
                )
            }
            else{
                it.findNavController().navigate(R.id.action_callScreen_to_contactsFragment)
            }
        }
        binding?.ivBack?.setOnClickListener {
            it.findNavController().popBackStack()
        }
       
    }
    private fun showVideo(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getVideoFromDb(adapterModel._id)?.let {
                withContext(Dispatchers.Main){
                    viewModel.videoUri = Uri.parse(it.uri)
                    loadUriToPlayer(Uri.parse(it.uri))
                }
            }?:download()
        }
    }
    private fun initDb(){
        viewModel.db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "call_videos_db"
        ).build()
    }
    private fun loadUriToPlayer(uri:Uri){
        val mediaItem = MediaItem.fromUri(uri)
        binding?.ivThumb?.isVisible = false
        binding?.pVFullVideo?.player?.setMediaItem(mediaItem)
        binding?.pVFullVideo?.player?.prepare()
        binding?.pVFullVideo?.player?.playWhenReady  = true
        binding?.pVFullVideo?.player?.repeatMode = ExoPlayer.REPEAT_MODE_ONE

    }
    private fun download(){
        lifecycleScope.launch {
            withContext(Dispatchers.Main){
                binding?.btnApply?.isEnabled = false
                binding?.btnContacts?.isVisible = false
                binding?.ivThumb?.let { Glide.with(requireContext()).load(adapterModel.prevyu).into(it) }
            }
            viewModel.retrieve(adapterModel.title+System.currentTimeMillis().toString(),"desc", adapterModel.theme, requireContext()).onCompletion {
                delay(1000)
                val uri =viewModel.downloadManager?.getUriForDownloadedFile(viewModel.id)
                withContext(Dispatchers.IO){
                    uri?.let {
                        viewModel.videoUri = uri
                        viewModel.insertDb( VideoModel(adapterModel._id,uri.toString()) )
                        viewModel.updateDb(DataModel(adapterModel._id,adapterModel.title,adapterModel.theme,adapterModel.isPremium,adapterModel.__v,adapterModel.prevyu,true))
                    }?: withContext(Dispatchers.Main){
                        binding?.btnApply?.let { it1 -> Snackbar.make(it1,"smth went wrong, try again", Snackbar.LENGTH_LONG).show() }
                    }
                 }
                withContext(Dispatchers.Main){
                    binding?.btnApply?.isEnabled = true
                    binding?.btnContacts?.isVisible = true
                    binding?.btnApply?.text = getString(R.string.applyIt)
                    uri?.let { it1 -> loadUriToPlayer(it1) }
                }
            }.collect{
                val progress = getString(R.string.download)+" "+ it.progress+"%"
                binding?.btnApply?.text = progress
            }
        }
    }


}