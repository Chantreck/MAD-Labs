package com.tsu.itindr.chat_activity.model

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatScrollListener(private val load: () -> Unit) : RecyclerView.OnScrollListener() {
    private var directionCheck = true

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE || directionCheck) return

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val adapter = recyclerView.adapter ?: return
        if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
            load.invoke()
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        directionCheck = dy >= 0
    }
}