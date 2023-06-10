/*
 *
 *  Created by paulz on 2023/6/8 下午4:07
 *  Last modified 2023/6/6 下午11:13
 */

package com.example.taipeitravel.uiAttractionDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taipeitravel.MainActivity
import com.example.taipeitravel.databinding.FragmentAttractionDetailBinding
import com.example.taipeitravel.repository.DataRepository
import com.example.taipeitravel.uiAttractionDetail.composeUI.AttractionDetailCompose

/**
 * 景點內頁fragment 包裝composeUI用
 */
class AttractionDetailFragment : Fragment() {

    private var _binding: FragmentAttractionDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //region lifeCycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAttractionDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    private fun setup() {
        (context as MainActivity).isShowOptionsMenu(false)
        (context as MainActivity).setAppBarTitle(DataRepository.currentAttraction!!.name)
        binding.attractionDetailComposeView.setContent {
            AttractionDetailCompose(attraction = DataRepository.currentAttraction!!)
        }
    }
}