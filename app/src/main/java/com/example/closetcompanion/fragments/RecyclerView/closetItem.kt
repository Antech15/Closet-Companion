package com.example.closetcompanion.fragments.RecyclerView

import android.widget.ImageView
import java.io.Serializable

data class closetItem (
    val title: String,
    val type: String,
    val image: ImageView,
): Serializable