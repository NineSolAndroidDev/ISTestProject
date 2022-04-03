package com.example.istestproject.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.istestproject.databinding.FragmentViewItemDetailsBinding


class ViewItemDetails : Fragment() {

    private val args: ViewItemDetailsArgs by navArgs()


    private lateinit var bindingViewDetails: FragmentViewItemDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingViewDetails = FragmentViewItemDetailsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return bindingViewDetails.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(bindingViewDetails)
        {
            viewItemsDetailbtn.setOnClickListener {


                val token = args.currentToken

                token?.let {

                    val action =
                        ViewItemDetailsDirections.actionViewItemDetailsToDetailsListFragment(it)
                    findNavController().navigate(action)
                }


            }


        }


    }


}