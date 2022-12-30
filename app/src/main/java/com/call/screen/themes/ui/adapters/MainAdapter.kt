package com.call.screen.themes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.call.screen.themes.R
import com.call.screen.themes.data.model.AdapterModel
import com.call.screen.themes.databinding.ItemAllowPermissionsBinding
import com.call.screen.themes.databinding.ItemCallScreensBinding
import com.call.screen.themes.singleton.CallApplication

const val VIEW_TYPE_PERMISSION = 2
const val VIEW_TYPE_ITEM = 1
class MainAdapter(val permissions:Boolean): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<AdapterModel>() {
        override fun areItemsTheSame(oldItem: AdapterModel, newItem: AdapterModel): Boolean {
            return oldItem._id == newItem._id
        }
        override fun areContentsTheSame(oldItem: AdapterModel, newItem: AdapterModel): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    inner class MainViewHolder( val binding: ItemCallScreensBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(adapterModel: AdapterModel){
             Glide.with(binding.vScreen.context).load(adapterModel.prevyu).transition(
                DrawableTransitionOptions.withCrossFade(100)).into(binding.vScreen)
            binding.tvTitle.text = adapterModel.title
            Glide.with(binding.ivProfile.context).load(adapterModel.gender).into(binding.ivProfile)
            binding.tvName.text = adapterModel.name
            binding.mView.setOnClickListener {
                CallApplication.adapterModel = adapterModel
                it.findNavController().navigate(R.id.action_mainCallFragment_to_callScreen)
            }
            Glide.with(binding.rbDownloaded.context).load(adapterModel.isDownloaded).into(binding.rbDownloaded)
        }
    }
    inner class PermissionViewHolder(private val binding: ItemAllowPermissionsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(categoryName: AdapterModel){
            binding.cvMain.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainCallFragment_to_dialogPermissions)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType== VIEW_TYPE_PERMISSION){
            PermissionViewHolder(ItemAllowPermissionsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        } else {
            MainViewHolder(ItemCallScreensBinding.inflate(LayoutInflater.from(parent.context),parent,false))

        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MainViewHolder){
            val foodItem = differ.currentList[position]
            holder.bind(foodItem)
        }
        if (holder is PermissionViewHolder){
            val foodItem = differ.currentList[position]
            holder.bind(foodItem)
        }
//        if (holder is PermissionViewHolder&&ss ){
//            val foodItem = differ.currentList[position]
//            holder.bind(foodItem)
//        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position==1&& permissions){
            VIEW_TYPE_PERMISSION
        } else{
            VIEW_TYPE_ITEM
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}