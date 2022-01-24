package com.tsu.silentmoon.course_details_activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.FragmentNarratorsBinding

class NarratorsFragment : Fragment(R.layout.fragment_narrators) {
    private lateinit var binding: FragmentNarratorsBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNarratorsBinding.bind(view);
        super.onViewCreated(view, savedInstanceState);

        val recyclerView: RecyclerView = binding.containerRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context);
            adapter = NarratorRVAdapter(context);
        }

        val decoration = DividerItemDecoration(recyclerView.context, RecyclerView.VERTICAL)
        AppCompatResources.getDrawable(recyclerView.context, R.drawable.drawable_divider)?.let {
            decoration.setDrawable(
                it
            )
        };
        recyclerView.addItemDecoration(decoration)
    }
}