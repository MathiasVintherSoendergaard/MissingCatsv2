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


        catsViewModel.catsLiveData.observe(viewLifecycleOwner) {cats ->
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
        }

        catsViewModel.reload()


        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.buttonGoToLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_logInFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // boolean Return false to allow normal menu processing to proceed, true to consume it here.
        // https://itecnote.com/tecnote/android-should-android-onoptionsitemselected-return-true-or-false/
        Log.d("APPLE", item.toString())
        when (item.itemId) {
            R.id.action_settings ->
                Snackbar.make(binding.catlistfragment, "Settings..", Snackbar.LENGTH_LONG).show()
            R.id.action_logout ->
                Snackbar.make(binding.catlistfragment, "Logout..", Snackbar.LENGTH_LONG).show()
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}