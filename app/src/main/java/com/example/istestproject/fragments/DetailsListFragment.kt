package com.example.istestproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.istestproject.adapter.DetailsItemsAdapter
import com.example.istestproject.databinding.FragmentDetailsListBinding
import com.example.istestproject.di.RequestStates
import com.example.istestproject.model.Item
import com.example.istestproject.viewmodels.DetailsListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsListFragment : Fragment() {

    private val viewItemsViewModel: DetailsListViewModel by viewModels()
    private lateinit var binding: FragmentDetailsListBinding
    val args: DetailsListFragmentArgs by navArgs()

    private val itemDetailsAdapter =
        DetailsItemsAdapter { currentItem -> itemClick(currentItem) }

    private fun itemClick(
        item: Item
    ) {


        val showVal = item.price
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailsListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        args.requestToken?.let {
            viewItemsViewModel.getProducts(it)
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding)
        {
            DetailsListItemsRv.apply {
                adapter = itemDetailsAdapter
                setHasFixedSize(true)
            }

        }


        viewItemsViewModel.getProductListLiveData.observe(viewLifecycleOwner)
        {
            when (it) {
                is RequestStates.Loading -> {

                    binding.progressCircular.isVisible = true

                }
                is RequestStates.Success -> {
                    binding.progressCircular.isVisible = false
                    val data = it.data?.itemList
                    itemDetailsAdapter.submitList(data)
                }
                is RequestStates.Error -> {
                    binding.progressCircular.isVisible = false
                }
            }

        }


    }


}