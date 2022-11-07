package com.example.missingcatsv2.Fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.missingcatsv2.Models.AuthenticationViewModel
import com.example.missingcatsv2.Models.CatsViewModel
import com.example.missingcatsv2.R
import com.example.missingcatsv2.databinding.FragmentSingleCatBinding

class SingleCatFragment : Fragment() {
    // instantiation of bindings
    private var _binding: FragmentSingleCatBinding? = null
    private val binding get() = _binding!!
    // instantiation of gesture detector
    private lateinit var mDetector: GestureDetectorCompat
    // instantiation of CatsViewModel and AuthenticationViewModel
    private val catsViewModel: CatsViewModel by activityViewModels()
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleCatBinding.inflate(inflater, container, false)

        mDetector = GestureDetectorCompat(requireContext(), MyGestureListener())
        val rootView: LinearLayout = binding.root
        // TODO: what is this warning message?
        rootView.setOnTouchListener { view, motionEvent ->
            mDetector.onTouchEvent(motionEvent)
            Log.d("APPLE", "Touch: " + motionEvent.x + " " + motionEvent.y)
            true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // below three lines make sure SafeArgs work
        val bundle = requireArguments()
        val singleCatFragmentArgs: SingleCatFragmentArgs = SingleCatFragmentArgs.fromBundle(bundle)
        val position = singleCatFragmentArgs.position
        // Finding the cat that is to be presented on the fragment
        val cat = catsViewModel[position]
        // If cat is null, show error message
        if (cat == null) {
            binding.textviewSecond.text = "No such cat!"
            return
        }
        // if cat is not null, show the long description of the cat
        else {
            binding.catPresentation.text = cat.toLongString()
        }
        // checks AuthenticationViewModel for logged in user. If user is logged in,
        // and the user's id is similar to the cat's user's id, the delete button is shown
        if(authenticationViewModel.userMutableLiveData.value?.email == cat.userId) {
            binding.buttonDeleteCat.visibility = View.VISIBLE
        }
        // navigation button back to cat list fragments
        // TODO: delete?
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            catsViewModel.getCats()
        }
        // delete button, sends user back to cat list fragment when cat is deleted
        binding.buttonDeleteCat.setOnClickListener {
            // TODO: Why does list of cats not auto-update when a cat is deleted?
            catsViewModel.delete(cat.id)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        // TODO: both horizontal and vertical swipes trigger the function - only horizontal should work
        // TODO: understand and comment logic of gesture detection
        // Minimum distance and velocity for the gesture to be reacted upon
        private val SWIPE_MIN_DISTANCE = 50
        private val SWIPE_THRESHOLD_VELOCITY = 50

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            try {
                // left to right swipe
                if (e2!!.x - e1!!.x > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
                ) {
                    moveBackToCatList(e2, e1)
                } else if (e1.x - e2.x > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
                ) {
                    //DO SOMETHING...
                }
            } catch (e: Exception) {
                return false
            }
            return true
        }

        private fun moveBackToCatList(ev1: MotionEvent, ev2: MotionEvent) {
            val xDiff = ev1.x - ev2.x
            if (xDiff > 0) {
                catsViewModel.getCats()
                findNavController() // inner keyword on MyGesture Listener
                    .navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
    }
}