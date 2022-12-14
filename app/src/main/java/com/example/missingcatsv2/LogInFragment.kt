package com.example.missingcatsv2

import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
/*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
 */
import com.example.missingcatsv2.Models.AuthenticationViewModel
import com.example.missingcatsv2.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
// import okhttp3.internal.wait

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LogInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogInFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()
    private lateinit var mDetector: GestureDetectorCompat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)

        mDetector = GestureDetectorCompat(requireContext(), MyGestureListener())
        val rootView: LinearLayout = binding.root
        // TODO: what does this warning message mean?
        rootView.setOnTouchListener { view, motionEvent ->
            mDetector.onTouchEvent(motionEvent)
            true
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = authenticationViewModel.userMutableLiveData.value

        binding.messageView.text = "Current user ${currentUser?.email}"

        binding.buttonSignIn.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailInputField.error = "No email"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordInputField.error = "No password"
                return@setOnClickListener
            }

            authenticationViewModel.logIn(email, password)

            // TODO: why does line 70 not update messageView?
            // update: there is, for some reason, no value in the string as it stands
            binding.messageView.text = "Current user ${authenticationViewModel.userMutableLiveData.value?.toString()}"
            // TODO: how do I move back to catlist fragment when a login has been succesful?
            /*
            val navController = findNavController()
            navController.popBackStack(R.id.catlistfragment, false)
             */

        }
        binding.buttonCreateUser.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.passwordInputField.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "No password"
                return@setOnClickListener
            }
            authenticationViewModel.register(email, password)
            /*
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.messageView.text = "User created. New please log in"
                } else {
                    binding.messageView.text = task.exception?.message
                }
            }
             */
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_MIN_DISTANCE = 50
        private val SWIPE_THRESHOLD_VELOCITY = 50

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            try {
                if (e2!!.x - e1!!.x > SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    moveBackToCatList(e2, e1)
                } else if (e1.x - e2.x > SWIPE_MIN_DISTANCE &&
                        Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    // TODO: why is this empty?
                }
            } catch (e: Exception) {
                return false
            }
            return true
        }

        private fun moveBackToCatList(ev1: MotionEvent, ev2: MotionEvent) {
            val xDiff = ev1.x - ev2.x
            if (xDiff > 0) {
                findNavController().navigate(R.id.action_logInFragment_to_FirstFragment)
            }
        }
    }
}
