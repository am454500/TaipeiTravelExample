/*
 *
 *  Created by paulz on 2023/6/7 上午12:41
 *  Last modified 2023/6/7 上午12:37
 */

/*
 *
 *  Created by paulz on 2023/6/7 上午12:36
 *  Last modified 2023/6/6 下午11:13
 */

package com.example.taipeitravel.uiAttractions.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taipeitravel.MainActivity
import com.example.taipeitravel.R
import com.example.taipeitravel.api.Resource
import com.example.taipeitravel.databinding.FragmentAttractionsBinding
import com.example.taipeitravel.enum.EnumLanguage
import com.example.taipeitravel.model.Attraction.Attraction
import com.example.taipeitravel.repository.DataRepository
import com.example.taipeitravel.uiAttractions.AttractionsViewModel
import com.example.taipeitravel.uiAttractions.adapter.AttractionsAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AttractionsFragment : Fragment(), AttractionsAdapter.AttractionsAdapterInterface {

    private var _binding: FragmentAttractionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    val binding get() = _binding!!
    private val viewModel: AttractionsViewModel by viewModels()
    private val adapter: AttractionsAdapter by lazy { AttractionsAdapter(this) }

    //region lifeCycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAttractionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        setupObserve()
    }

    override fun onResume() {
        (context as MainActivity).isShowOptionsMenu(true)
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    private fun setup() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        ArrayAdapter(requireContext(),
            android.R.layout.simple_list_item_1,
            EnumLanguage.values().map { it.displayName }).also {
            binding.languagesSpinner.adapter = it
            binding.languagesSpinner
        }
        binding.languagesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               viewModel.languageLiveData.value = EnumLanguage.values()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun setupObserve() {
        viewModel.attractionLiveData.observe(viewLifecycleOwner) { liveData ->
            when (liveData) {
                is Resource.Success -> {
                    adapter.submitList(liveData.data)
                }
                is Resource.Error -> Toast.makeText(requireContext(),"error: ${liveData.message}",Toast.LENGTH_LONG).show()
                is Resource.Loading -> {

                }
            }
            binding.progressCircular.isVisible = liveData is Resource.Loading
        }
        viewModel.languageLiveData.observe(viewLifecycleOwner) { liveData ->
            viewModel.fetchFeedAttractions(languageCode = liveData.languageCode)
        }
    }

    override fun attractionSelected(attraction: Attraction) {
        DataRepository.currentAttraction = attraction
        findNavController().navigate(R.id.action_AttractionsFragment_to_AttractionDetailFragment)
    }
}