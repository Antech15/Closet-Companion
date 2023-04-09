package com.example.closetcompanion.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.closetcompanion.R
import com.example.closetcompanion.activities.HomePage
import com.example.closetcompanion.data.FirestoreUtil

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

        view.findViewById<Button>(R.id.login_to_landing_button).setOnClickListener {
            userNameEditText.error = ""
            switchVisibility(progressbar)
            val username = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()
            FirestoreUtil.db.collection("Users").whereEqualTo("username", username).get().addOnSuccessListener {
                for (document in it.documents){
                    if(document.getString("username") != username){
                        continue
                    }

                    if(document.getString("password") != password){
                        switchVisibility(progressbar)
                        Toast.makeText(this.context, "Incorrect Username or Password.\nPlease Try Again.", Toast.LENGTH_LONG).show()
                        break
                    }

                    //Handler().postDelayed({
                    startActivity(Intent(this.context, HomePage::class.java))
                    //},1000)
                }
            }
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