package com.sergiofierro.mailapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.sergiofierro.mailapp.R
import com.sergiofierro.mailapp.databinding.FragmentMainBinding
import com.sergiofierro.mailapp.ui.adapter.PostAdapter
import com.sergiofierro.mailapp.util.SwipeToDeleteTouchHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceholderFragment : Fragment() {

  private val pageViewModel: PageViewModel by viewModels()
  private lateinit var binding: FragmentMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    pageViewModel.setListType(arguments?.getSerializable(ARG_SECTION) as ListType)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
    binding.apply {
      lifecycleOwner = this@PlaceholderFragment
      val postAdapter = PostAdapter { post ->
        pageViewModel.deletePost(post)
      }
      adapter = postAdapter
      vm = pageViewModel
      recyclerView.setHasFixedSize(true)
      recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
      val itemTouchHelper = ItemTouchHelper(SwipeToDeleteTouchHelper(postAdapter))
      itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    return binding.root
  }

  companion object {

    private const val ARG_SECTION = "section"

    @JvmStatic
    fun newInstance(section: ListType): PlaceholderFragment = PlaceholderFragment().apply {
      arguments = Bundle().apply {
        putSerializable(ARG_SECTION, section)
      }
    }
  }
}
