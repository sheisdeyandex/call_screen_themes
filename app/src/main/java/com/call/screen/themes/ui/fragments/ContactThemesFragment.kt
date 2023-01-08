package com.call.screen.themes.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.call.screen.themes.Constants
import com.call.screen.themes.R
import com.call.screen.themes.data.model.ContactsModel
import com.call.screen.themes.databinding.FragmentContactThemesBinding
import com.call.screen.themes.singleton.CallApplication
import com.call.screen.themes.ui.adapters.ContactThemesAdapter
import com.call.screen.themes.ui.adapters.ContactsAdapter
import com.call.screen.themes.viewmodels.ContactThemesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactThemesFragment : Fragment() {

    private val viewModel: ContactThemesViewModel by viewModels()
    private lateinit var binding:FragmentContactThemesBinding
    private lateinit var adapter: ContactThemesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentContactThemesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sharedPrefs = requireActivity().getSharedPreferences(Constants.sharedPrefsContactsName,MODE_PRIVATE)
        adapter = ContactThemesAdapter()
        binding.rvContacts.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.rvContacts.adapter = adapter
        viewModel.getArrayList()?.let {
            adapter.differ.submitList(viewModel.getArrayList())
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivDelete.setOnClickListener {
            binding.ivDelete.isVisible=false
            val newList = ArrayList<ContactsModel>()
            viewModel.getArrayList()?.forEach {
                newList.add(ContactsModel(it.id,it.name,it.number,it.lookUpKey,it.photo,it.theme,true))
            }
            adapter.differ.submitList(newList)
            binding.btnDelete.isVisible=true
        }
        adapter.contactsToDelete.observe(viewLifecycleOwner){
            binding.btnDelete.isEnabled = it.isNotEmpty()
        }
        binding.btnDelete.setOnClickListener {
            findNavController().navigate(R.id.action_contactThemes_to_dialogDeleteTheme)
        }
        deleteObserver()
    }
    fun delete(){
        val newMainContactsList = ArrayList<ContactsModel>()
        viewModel.getArrayList()?.forEach { mainContacts->
            adapter.contactsList.forEach { contactsToDelete->
                if (mainContacts.id!=contactsToDelete.id){
                    newMainContactsList.add(mainContacts)
                }
            }


        }
        viewModel.saveArrayList(newMainContactsList)
        val newList = ArrayList<ContactsModel>()
        viewModel.getArrayList().forEach {
            newList.add(ContactsModel(it.id,it.name,it.number,it.lookUpKey,it.photo,it.theme,true))
        }
        adapter.contactsList = ArrayList()
        adapter.contactsToDelete.postValue(adapter.contactsList)
        adapter.differ.submitList(newList)
    }
    fun deleteObserver(){
        CallApplication.deleteTheme.observe(viewLifecycleOwner){
            if (it){
                delete()
                CallApplication.deleteTheme.postValue(false)
            }
        }
    }
}