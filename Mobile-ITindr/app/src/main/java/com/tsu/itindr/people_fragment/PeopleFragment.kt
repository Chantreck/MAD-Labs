package com.tsu.itindr.people_fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tsu.itindr.R
import com.tsu.itindr.databinding.FragmentPeopleBinding
import com.tsu.itindr.model.utils.MessageShower
import com.tsu.itindr.people_fragment.model.PeopleAdapter
import com.tsu.itindr.people_fragment.model.PeopleMarginDecorator
import com.tsu.itindr.people_fragment.model.User
import com.tsu.itindr.user_profile_activity.UserProfileActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PeopleFragment : Fragment(R.layout.fragment_people) {
    private companion object {
        private const val USER_TAG = "userId"
    }

    private lateinit var binding: FragmentPeopleBinding
    private val viewModel by viewModels<PeopleViewModel>()

    private val personClickListener = object : PeopleAdapter.PersonClickListener {
        override fun onPersonClick(user: User) {
            val intent = Intent(context, UserProfileActivity::class.java)
            intent.putExtra(USER_TAG, user.userId)
            startActivity(intent)
        }
    }
    private val adapter by lazy { PeopleAdapter(personClickListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPeopleBinding.bind(view)

        initObservers()
        initRecycler()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.pager.flow.collectLatest { pagingData ->
                adapter.submitData(pagingData.map { it.toDto().toDomain() })
            }
        }

        viewModel.savedUsersCount.observe(this) {
            if (it > 0) {
                binding.progressBar.isVisible = false
            }
        }

        viewModel.errors.observe(this) {
            MessageShower.showToast(context, it)
        }
    }

    private fun initRecycler() {
        val recyclerManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        binding.chatsRecyclerView.layoutManager = recyclerManager
        binding.chatsRecyclerView.adapter = adapter
        binding.chatsRecyclerView.addItemDecoration(PeopleMarginDecorator())
        binding.chatsRecyclerView.setHasFixedSize(true)
    }
}