package com.call.screen.themes.ui.fragments

import android.Manifest
import android.Manifest.permission.READ_CONTACTS
import android.app.role.RoleManager
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.data.database.AppDatabase
import com.call.screen.themes.databinding.FragmentMainCallsBinding
import com.call.screen.themes.services.BroadcastService
import com.call.screen.themes.ui.MainActivity
import com.call.screen.themes.ui.adapters.MainAdapter
import com.call.screen.themes.usecases.InterUseCase
import com.call.screen.themes.usecases.SentEmail
import com.call.screen.themes.usecases.SpacesItemDecoration
import com.call.screen.themes.viewmodels.MainCallsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainCallsFragment : Fragment() {

    private val viewModel: MainCallsViewModel by viewModels()

    private var binding:FragmentMainCallsBinding? = null

    private var mainAdapter:MainAdapter? = null

    private var gridLayoutManager:StaggeredGridLayoutManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCallsBinding.inflate(inflater,container,false)
        return binding?.root
    }
    fun showRate(){
        val rateApp = (requireActivity() as MainActivity).rateApp
        rateApp.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigate(R.id.action_mainCallFragment_to_zeroStartFragment)
                rateApp.postValue(false)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            viewModel.roleManager= requireContext().getSystemService(AppCompatActivity.ROLE_SERVICE) as RoleManager
        }
        showRate()
        initRecycler()
        initDb()
        checkAndGetDataFromDb()
        binding?.ivMenu?.setOnClickListener {
            if (binding?.drawer?.isDrawerOpen(GravityCompat.START) == true){
                binding?.drawer?.closeDrawer(GravityCompat.START)
            }
            else{
                binding?.drawer?.openDrawer(GravityCompat.START)
            }
        }
        binding?.tvPrivacyPolicy?.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data =
                Uri.parse("https://ponica.media/call-screen/privacy-policy/")
            context?.startActivity(openURL)
        }
        binding?.tvContactTheme?.setOnClickListener {
            findNavController().navigate(R.id.action_mainCallFragment_to_contactThemes)
        }
        binding?.tvFeedBack?.setOnClickListener {
            SentEmail.sendEmail("",requireActivity())
        }
        binding?.tvShare?.setOnClickListener {
            SentEmail.share(requireContext())
        }
        InterUseCase.init(requireContext())

        initSharedPrefs()
    }
    private fun initSharedPrefs(){
        viewModel.sharedPreferences = context?.getSharedPreferences(Constants.sharedPrefsContactsName,MODE_PRIVATE)
        binding?.swFlashOnCall?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setFlashOnCall(isChecked)
        }
        viewModel.getFlashOnCall()?.let {
            binding?.swFlashOnCall?.isChecked=it
        }

    }
    private fun checkAndGetDataFromDb(){
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                val data = viewModel.getDataModelFromDb()
                if (data.isNullOrEmpty()){
                    initVideosList()
                }
                else{
                    withContext(Dispatchers.Main){
                        binding?.pgLoading?.isVisible= false
                        viewModel.makeAdapterList(data).collect{
                            binding?.pgLoading?.isVisible= false
                            mainAdapter = MainAdapter(checkPermissions())
                            binding?.rvCallVideos?.adapter = mainAdapter
                            mainAdapter?.differ?.submitList(it)
                        }

                    }
                }
            }
        }
    }
    private fun checkPermissions() :Boolean{
        var count =0
        if ( ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(requireContext(),READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
         count++
        }
        if (!Settings.canDrawOverlays(requireContext())){
            count++
        }
        if ( ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ANSWER_PHONE_CALLS)!= PackageManager.PERMISSION_GRANTED){
            count++
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            viewModel.checkRolePermission()?.let {
                if (!it){
                    count++
                }
            }

        }
        return if(count>0){
            binding?.swCallScreen?.isChecked = false
            findNavController().navigate(R.id.action_mainCallFragment_to_dialogPermissions)
            true
        } else{
            binding?.swCallScreen?.isChecked = true
            requireContext().startForegroundService(Intent(requireContext(),BroadcastService::class.java))
            false
        }
    }

    private fun initDb(){
        viewModel.db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "call_videos_db"
        ).build()
    }

    private fun initRecycler(){
        gridLayoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding?.rvCallVideos?.addItemDecoration(SpacesItemDecoration(30))
        binding?.rvCallVideos?.layoutManager = gridLayoutManager
        initServerError()
    }
    private fun initVideosList(){
        lifecycleScope.launch {
            withContext(Dispatchers.Main){
                viewModel.makeFreeRequest().collect{
                    binding?.pgLoading?.isVisible= false
                    mainAdapter = MainAdapter(checkPermissions())
                    binding?.rvCallVideos?.adapter = mainAdapter
                    mainAdapter?.differ?.submitList(it)
                }
            }
        }
    }

    private fun initServerError(){
        viewModel.error.observe(viewLifecycleOwner){
      //      binding?.main?.let { it1 -> Snackbar.make(it1, "server error: $it", Snackbar.LENGTH_LONG).show() }
            if (viewModel.count<=2){
                initVideosList()
            }
        }
    }
}