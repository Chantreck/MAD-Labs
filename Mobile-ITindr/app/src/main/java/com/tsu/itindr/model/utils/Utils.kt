package com.tsu.itindr.model.utils

import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.tsu.itindr.R
import com.tsu.itindr.model.database.topic.TopicEntity
import com.tsu.itindr.people_fragment.model.Topic

fun TextInputLayout.showError(error: Int) {
    this.error = this.context.getString(error)
}

fun TextInputLayout.hideError() {
    this.error = null
    this.isErrorEnabled = false
}

/* ImageUtils */

fun ImageView.setDefaultImage() {
    setImageResource(R.drawable.ic_default_profile)
}

fun ImageView.setCircleImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .circleCrop()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

fun ImageView.setImagePreview(url: String) {
    Glide.with(this.context)
        .load(url)
        .override(SIZE_ORIGINAL)
        .into(this)
}

/* ChipGroupUtils */

fun ChipGroup.setTopics(
    layoutInflater: LayoutInflater,
    topics: List<TopicEntity>,
) {
    removeAllViews()
    topics.forEach { topic ->
        addTopic(layoutInflater, topic.title, topic.isSelected)
    }
}

fun ChipGroup.setSelectedTopics(layoutInflater: LayoutInflater, topics: List<Topic>) {
    removeAllViews()
    topics.forEach { topic ->
        addTopic(layoutInflater, topic.title, true)
    }
}

private fun ChipGroup.addTopic(layoutInflater: LayoutInflater, text: String, checked: Boolean) {
    val chip = layoutInflater.inflate(R.layout.chip_layout, this, false) as Chip
    chip.text = text
    chip.isChecked = checked
    chip.isClickable = true
    chip.isCheckable = true
    addView(chip)
}

fun ChipGroup.getSelectedTopics(): List<String> {
    val list = mutableListOf<String>()
    for (i in 0 until childCount) {
        val chip = getChildAt(i) as Chip
        if (chip.isChecked) {
            list.add(chip.text as String)
        }
    }

    return list
}