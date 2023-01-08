package com.call.screen.themes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.call.screen.themes.data.model.ContactsModel
import com.call.screen.themes.databinding.ItemContactThemesBinding
import com.call.screen.themes.databinding.ItemContactsBinding

class ContactThemesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var contactsToDelete = MutableLiveData<ArrayList<ContactsModel>>()
    var visibility = false
    var contactsList = ArrayList<ContactsModel>()
    private val differCallback = object : DiffUtil.ItemCallback<ContactsModel>() {
        override fun areItemsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    inner class MainViewHolder(val binding: ItemContactThemesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(contactsModel: ContactsModel){
            binding.tvName.text = contactsModel.name
            Glide.with(binding.ivProfile.context).load(contactsModel.theme).into(binding.ivProfile)
            binding.rbContacts.isVisible = contactsModel.visible
            binding.rbContacts.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    contactsList.add(contactsModel)
                    contactsToDelete.postValue(contactsList)
                }
                else{
                    if(contactsList.contains(contactsModel)){
                        contactsList.remove(contactsModel)
                        contactsToDelete.postValue(contactsList)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainViewHolder(ItemContactThemesBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MainViewHolder){
            val foodItem = differ.currentList[position]
            holder.bind(foodItem)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}