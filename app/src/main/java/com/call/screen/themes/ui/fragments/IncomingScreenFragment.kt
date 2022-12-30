package com.call.screen.themes.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.data.database.AppDatabase
import com.call.screen.themes.data.model.AdapterModel
import com.call.screen.themes.data.model.MainModel
import com.call.screen.themes.data.model.VideoModel
import com.call.screen.themes.databinding.CallScreenBinding
import com.call.screen.themes.databinding.FragmentCallScreenBinding
import com.call.screen.themes.singleton.CallApplication
import com.call.screen.themes.viewmodels.CallScreenViewModel
import com.call.screen.themes.viewmodels.IncomingCallScreenViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class IncomingScreenFragment : Fragment() {
    private val viewModel: IncomingCallScreenViewModel by viewModels()
    private var binding:CallScreenBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CallScreenBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}