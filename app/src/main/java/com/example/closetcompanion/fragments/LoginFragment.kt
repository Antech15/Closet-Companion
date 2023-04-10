package com.example.closetcompanion.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.compose.runtime.derivedStateOf
import androidx.work.*
import com.example.closetcompanion.R
import com.example.closetcompanion.data.LoginWorker
import com.example.closetcompanion.data.WorkerKeys
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userNameEditText = view.findViewById<EditText>(R.id.login_username_edit_text)
        val passwordEditText = view.findViewById<EditText>(R.id.login_password_edit_text)
        val progressbar = view.findViewById<ProgressBar>(R.id.login_progress_bar)
        val loginButton = view.findViewById<Button>(R.id.login_to_landing_button)

        val workManager = WorkManager.getInstance(this.requireContext().applicationContext)

        var workInfos: MutableList<WorkInfo>

        view.findViewById<Button>(R.id.login_to_landing_button).setOnClickListener {
            userNameEditText.error = ""
            switchVisibility(progressbar)

            val username = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()

            //Creating a work Request and giving it a LoginWorker type to do the work.
            val loginRequest = OneTimeWorkRequestBuilder<LoginWorker>()
                .setInputData(
                    Data.Builder()
                        .putString(WorkerKeys.USERNAME, username)
                        .putString(WorkerKeys.PASSWORD, password)
                        .build()
                )
                .build()

            workManager.beginUniqueWork("Login", ExistingWorkPolicy.KEEP, loginRequest).enqueue()

            //Get a work manager instance
            //get the current work information
            workInfos = workManager.getWorkInfosForUniqueWork("Login").get()
            //Find the work information associated with the loginRequest by comparing IDs
            val loginRequestInfo =
                workInfos.find{
                    it.id == loginRequest.id
                }

            if(loginRequestInfo.outputData.getString(WorkerKeys.CORRECT_PASSWORD).toBoolean()){
                //Handler().postDelayed({
                startActivity(Intent(this.context, LandingFragment::class.java))
                //},1000)
            }
            else{
                Toast.makeText(this.context, "Username or Password is incorrect.\nPlease Try Again.", Toast.LENGTH_LONG).show()
            }
        }
        val userDocument by derivedStateOf {

        }
    }



    fun switchVisibility(progressBar: ProgressBar){
        if(progressBar.visibility == ProgressBar.INVISIBLE){
            progressBar.visibility = ProgressBar.VISIBLE
        }else{
            progressBar.visibility = ProgressBar.INVISIBLE
        }
    }
}