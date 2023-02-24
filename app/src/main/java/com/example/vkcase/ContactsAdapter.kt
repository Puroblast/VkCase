package com.example.vkcase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter() : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var oldContactsArray = arrayListOf<Person>()

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val avatarContact: ImageView = itemView.findViewById(R.id.photoIv)
        val nameTv : TextView = itemView.findViewById(R.id.contactName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.avatarContact.setImageResource(oldContactsArray[position].imageSrc)
        holder.nameTv.text = oldContactsArray[position].name
    }

    override fun getItemCount(): Int {
        return oldContactsArray.size
    }

    fun setData(newContactsList : ArrayList<Person>) {
        val diffCallback = PersonDiffUtill(oldContactsArray, newContactsList )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        oldContactsArray = newContactsList
        diffResult.dispatchUpdatesTo(this)
    }

}