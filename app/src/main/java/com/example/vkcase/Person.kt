package com.example.vkcase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Person(
    val name: String,
    val imageSrc: Int,
    val id : Int,
    val isMuted: Boolean = false

) : Parcelable
