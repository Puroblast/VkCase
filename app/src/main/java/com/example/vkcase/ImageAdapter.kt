package com.example.vkcase

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class ImageAdapter :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var oldPersons = arrayListOf<Person>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImage: ShapeableImageView = itemView.findViewById(R.id.avatarImage)
        val nameTv: TextView = itemView.findViewById(R.id.avatarName)
        val avatarBackground: ImageView = itemView.findViewById(R.id.avatarBackground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.avatarImage.setImageResource(oldPersons[position].imageSrc)
        holder.avatarBackground.setImageDrawable(gradientDrawable(holder))
        holder.nameTv.text = oldPersons[position].name
        if (oldPersons[position].isMuted) {
            holder.nameTv.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.mic_off_small_size,
                0
            )
        } else {
            holder.nameTv.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.mic_small_size,
                0
            )
        }
    }


    override fun getItemCount(): Int {
        return oldPersons.size
    }

    private fun gradientDrawable(holder: ViewHolder): GradientDrawable {
        val drawable = holder.avatarImage.drawable
        val firstColor = drawable.toBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight
        ).getPixel(0, drawable.intrinsicHeight / 2).toColor().toArgb()
        val secondColor = drawable.toBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight
        ).getPixel(
            drawable.intrinsicWidth - 1,
            drawable.intrinsicHeight / 2
        ).toColor().toArgb() * 10
        val newDrawable = GradientDrawable(
            GradientDrawable.Orientation.BL_TR,
            intArrayOf(firstColor, secondColor)
        )
        newDrawable.shape = GradientDrawable.RECTANGLE
        newDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        return newDrawable
    }

    fun setData(newPersonsList: ArrayList<Person>) {
        val diffCallback = PersonDiffUtill(oldPersons, newPersonsList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        oldPersons = newPersonsList
        diffResult.dispatchUpdatesTo(this)
    }


}