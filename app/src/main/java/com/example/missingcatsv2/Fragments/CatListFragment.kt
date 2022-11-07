package com.example.missingcatsv2.Fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.missingcatsv2.Models.AuthenticationViewModel
import com.example.missingcatsv2.Models.CatsViewModel
import com.example.missingcatsv2.MyAdapter
import com.example.missingcatsv2.R
import com.example.missingcatsv2.databinding.FragmentCatListBinding


class CatListFragment : Fragment() {

    private var _binding: FragmentCatListBinding? = null

    private val binding get() = _binding!!
    // ViewModels for cats and authentication, respectively
    private val catsViewModel: CatsViewModel by activityViewModels()
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // AuthenticationViewModel checks if there is a user currently logged in
        // if yes, button to create a cat is shown
        authenticationViewModel.userMutableLiveData.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                binding.buttonCreateCat.visibility = View.VISIBLE
            }
        }
        // CatsViewModel checks if there are any cats in the cat list
        // if not, the recyclerview which holds the cats is not shown
        catsViewModel.catsLiveData.observe(viewLifecycleOwner) { cats ->
            binding.recyclerView.visibility = if (cats == null) {
                View.GONE
            } else {
                View.VISIBLE
            }
            // TODO: remove all Log.d statements - when the app is done, that is
            Log.d("Apple", "We are before if")
            // TODO: what is going on down here?
            // If there are cats to display, an Adapter is instantiated
            if (cats != null) {
                // TODO: would be nice if the logged in user's own cats were marked in another color
                // Adapter makes sure that RecyclerView in cat list fragment knows what kind of data it
                // is supposed to display, i.e. adapts generic RecyclerView to specific Cat data
                val adapter = MyAdapter(cats) { position ->
                    Log.d("Apple", "Element number $position was pressed")
                    // an action is instantiated for when an item in the RecyclerView is clicked,
                    // i.e. when a cat in the list is clicked, move to fragment displaying that cat
                    val action =
                        CatListFragmentDirections.actionFirstFragmentToSecondFragment(position)
                    findNavController().navigate(action)
                }
                // this section sets number of columns in the RecyclerView, depending on orientation
                var columns = 2
                val currentOrientation = this.resources.configuration.orientation
                if(currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }

                // LayoutManager arranges the individual elements in the list. A GridLayoutmanager
                // is instantiated, with the number of columns from the lines above
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)
                // the adapter is given to the RecyclerView
                binding.recyclerView.adapter = adapter
            }
    }
        // when create cat button is clicked, move to create cat fragment
        binding.buttonCreateCat.setOnClickListener {
            findNavController().navigate(R.id.action_CatListFragment_to_createCatFragment)
        }

        binding.buttonSort.setOnClickListener {
            when (binding.spinnerSorting.selectedItemPosition) {
                0 -> catsViewModel.sortByName()
                1 -> catsViewModel.sortByNameDescending()
                2 -> catsViewModel.sortByDate()
                3 -> catsViewModel.sortByDateDescending()
            }
        }

}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





