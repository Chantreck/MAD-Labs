package com.tsu.itindr.people_fragment.model

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tsu.itindr.R

class PeopleMarginDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val margin = parent.context.resources.getDimensionPixelOffset(R.dimen.margin)
        val halfMargin = parent.context.resources.getDimensionPixelOffset(R.dimen.margin_half)
        val additionalMargin = parent.context.resources.getDimensionPixelOffset(R.dimen.margin_4x)

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return
        }

        outRect.top = margin
        outRect.bottom = halfMargin

        if (position < 3) {
            when (position % 3) {
                0 -> {
                    outRect.left = margin
                    outRect.right = halfMargin
                }
                1 -> {
                    outRect.left = halfMargin
                    outRect.right = halfMargin
                    outRect.top += additionalMargin
                }
                2 -> {
                    outRect.right = margin
                    outRect.left = halfMargin
                }
            }
        } else {
            when (position % 3) {
                0 -> {
                    outRect.left = margin
                    outRect.right = halfMargin
                }
                1 -> {
                    outRect.right = margin
                    outRect.left = halfMargin
                }
                2 -> {
                    outRect.left = halfMargin
                    outRect.right = halfMargin
                }
            }
        }
    }
}