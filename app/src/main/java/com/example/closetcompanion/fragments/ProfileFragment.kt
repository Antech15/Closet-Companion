package com.example.closetcompanion.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.closetcompanion.data.Images
import com.example.closetcompanion.R
import com.example.closetcompanion.models.User
import com.example.closetcompanion.data.ImageDao
import com.example.closetcompanion.data.ImageDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.net.Uri
import com.example.closetcompanion.activities.HomePage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: User? = null
    private var param2: String? = null

    private lateinit var imageDatabase: ImageDatabase
    private lateinit var imageDao: ImageDao
    private lateinit var profileImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val bundle = arguments
            param1 = bundle?.getParcelable<User>("user")
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize imageDatabase and imageDao
        imageDatabase = ImageDatabase.getDatabase(requireContext())
        imageDao = imageDatabase.imageDao()
        profileImage = view.findViewById(R.id.profile_picture_image)

        // Load the image from the room when the fragment is created
        loadImage()

        // Set an OnClickListener to open the gallery when the profile picture is clicked
        profileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        return view
    }

    // Handle the result of the gallery intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val homeActivity = requireActivity() as HomePage

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data.toString()
            // Insert the selected image into the room
            GlobalScope.launch {
                imageDao.insertImage(Images(imageUri = imageUri))
            }
            // Load the selected image into the ImageView
            profileImage.setImageURI(Uri.parse(imageUri))
            homeActivity.image = imageUri

            // Delete any previous images from the room
            GlobalScope.launch {
                imageDao.deleteAllImages()
            }
        }
    }

    private fun loadImage() {

        GlobalScope.launch {
            val homeActivity = requireActivity() as HomePage


            val images = imageDao.getAllImages()
            if(homeActivity.image != null) {
                profileImage.setImageURI(Uri.parse(homeActivity.image))
            }
            else if (images.isNotEmpty()) {
                val imageUri = images[0].imageUri
                profileImage.setImageURI(Uri.parse(imageUri))
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        // Room local variable
        const val PICK_IMAGE_REQUEST = 1
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usernameTextField = view.findViewById<TextView>(R.id.username_text_field)
        val nameTextField = view.findViewById<TextView>(R.id.name_text_field)
        val emailTextField = view.findViewById<TextView>(R.id.email_text_field)
        val dobTextField = view.findViewById<TextView>(R.id.dob_text_field)

        val name = param1?.first_name + " " + param1?.last_name
        usernameTextField.text = param1?.username
        nameTextField.text = name
        emailTextField.text = param1?.email_address
        dobTextField.text =  param1?.dob
    }
}