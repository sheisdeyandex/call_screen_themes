package com.call.screen.themes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.call.screen.themes.data.model.ContactsModel
import com.call.screen.themes.databinding.ItemContactThemesBinding
import com.call.screen.themes.databinding.ItemContactsBinding

class ContactsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var addedContactsList:ArrayList<ContactsModel> = ArrayList()
    private val differCallback = object : DiffUtil.ItemCallback<ContactsModel>() {
        override fun areItemsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    inner class MainViewHolder(val binding: ItemContactsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(contactsModel: ContactsModel){
            binding.tvName.text = contactsModel.name
            binding.rbContacts.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    addedContactsList.add(contactsModel)
                }
                else{
                    if (addedContactsList.contains(contactsModel)){
                        addedContactsList.remove(contactsModel)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainViewHolder(ItemContactsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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