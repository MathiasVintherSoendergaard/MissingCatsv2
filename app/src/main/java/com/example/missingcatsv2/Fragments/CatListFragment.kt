package com.example.missingcatsv2.Fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.missingcatsv2.Models.CatsViewModel
import com.example.missingcatsv2.MyAdapter
import com.example.missingcatsv2.R
import com.example.missingcatsv2.databinding.FragmentCatListBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CatListFragment : Fragment() {

    private var _binding: FragmentCatListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val catsViewModel: CatsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCatListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catsViewModel.catsLiveData.observe(viewLifecycleOwner) { cats ->
            binding.recyclerView.visibility = if (cats == null) View.GONE else View.VISIBLE

            if (cats != null) {
                val adapter = MyAdapter(cats) { position ->
                    val action =
                        CatListFragmentDirections.actionFirstFragmentToSecondFragment(position)
                    findNavController().navigate(action)
                }

                var columns = 2

                val currentOrientation = this.resources.configuration.orientation
                if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }

                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recyclerView.adapter = adapter
            }
        catsViewModel.reload()
            // nedenstående er til "next"-knappen, som jo nok skal slettes
        binding.buttonFirst.setOnClickListener {
            Log.d("Next button was pressed", "Next button was pressed")
            val action =
                CatListFragmentDirections.actionFirstFragmentToSecondFragment(1)
            findNavController().navigate(action)
        }
        binding.buttonGoToLogIn.setOnClickListener {
            Log.d("Go to login button was pressed", "Go to login button was pressed")
            findNavController().navigate(R.id.action_FirstFragment_to_logInFragment)
        }
    }

}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





