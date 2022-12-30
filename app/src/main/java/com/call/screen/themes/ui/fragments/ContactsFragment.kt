package com.call.screen.themes.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.call.screen.themes.data.model.ContactsModel
import com.call.screen.themes.databinding.FragmentContactsBinding
import com.call.screen.themes.ui.adapters.ContactsAdapter
import com.call.screen.themes.viewmodels.ContactsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by viewModels()
    lateinit var binding:FragmentContactsBinding
    lateinit var contactsAdapter: ContactsAdapter
    val list = ArrayList<ContactsModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContactsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactsAdapter = ContactsAdapter()
        initRecycleView()
        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.btnSet.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.ivSearch.setOnClickListener {
            binding.ivSearch.visibility = View.INVISIBLE
            binding.tvTitle.visibility = View.INVISIBLE
            binding.tilSearch.visibility = View.VISIBLE
            binding.edSearch.requestFocus()
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(binding.edSearch, InputMethodManager.SHOW_IMPLICIT)
        }
        binding.edSearch.doOnTextChanged { text, start, before, count ->
            val newList = ArrayList<ContactsModel>()
            list.forEach {
                if (it.name.lowercase().contains(text.toString())){
                    newList.add(it)
                }
            }.also {
                contactsAdapter.differ.submitList(newList)
            }
        }
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(requireActivity(), object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.ivSearch.visibility==View.INVISIBLE){
                        binding.ivSearch.isVisible=true
                        binding.tvTitle.isVisible=true
                        binding.tilSearch.isVisible = false
                    }
                    if (isEnabled) {
                        isEnabled = false
                    }
                }
            }
            )
    }
    private fun initRecycleView(){
        binding.rvContacts.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvContacts.adapter = contactsAdapter
        lifecycleScope.launch{

            withContext(Dispatchers.IO){
                list.addAll(viewModel.getContacts(requireContext()))
            }
            withContext(Dispatchers.Main){
                contactsAdapter.differ.submitList(list)
            }
        }
    }
}