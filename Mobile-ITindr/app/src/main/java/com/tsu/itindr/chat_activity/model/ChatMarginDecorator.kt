package com.tsu.itindr.chat_activity.model

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tsu.itindr.R

class ChatMarginDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val margin = parent.context.resources.getDimensionPixelOffset(R.dimen.margin)
        val additionalMargin = parent.context.resources.getDimensionPixelOffset(R.dimen.margin_half)

        val adapter = parent.adapter ?: return

        when (val position = parent.getChildAdapterPosition(view)) {
            RecyclerView.NO_POSITION -> return
            in 0..(adapter.itemCount - 2) -> {
                if (adapter.getItemViewType(position) == adapter.getItemViewType(position + 1)) {
                    outRect.top = additionalMargin
                } else {
                    outRect.top = margin + additionalMargin
                }
                if (position == 0) {
                    outRect.bottom = margin
                }
            }
            adapter.itemCount - 1 -> outRect.top = margin
        }
    }
}