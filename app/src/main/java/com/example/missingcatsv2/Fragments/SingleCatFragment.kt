package com.example.missingcatsv2.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.missingcatsv2.Models.AuthenticationViewModel
import com.example.missingcatsv2.Models.CatsViewModel
import com.example.missingcatsv2.R
import com.example.missingcatsv2.databinding.FragmentSingleCatBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SingleCatFragment : Fragment() {
    private var _binding: FragmentSingleCatBinding? = null
    private val binding get() = _binding!!
    private val catsViewModel: CatsViewModel by activityViewModels()
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val singleCatFragmentArgs: SingleCatFragmentArgs = SingleCatFragmentArgs.fromBundle(bundle)
        val position = singleCatFragmentArgs.position
        val cat = catsViewModel[position]
        if (cat == null) {
            binding.textviewSecond.text = "No such cat!"
            return
        } else {
            binding.catPresentation.text = cat.toLongString()
        }

        if(authenticationViewModel.userMutableLiveData.value?.email == cat.userId) {
            binding.buttonDeleteCat.visibility = View.VISIBLE
        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.catPresentation.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}