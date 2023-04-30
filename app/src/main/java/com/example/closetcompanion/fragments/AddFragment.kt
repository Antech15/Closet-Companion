import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import com.example.closetcompanion.R
import com.example.closetcompanion.activities.HomePage
import com.example.closetcompanion.data.LoginWorker
import com.example.closetcompanion.data.WorkerKeys
import com.example.closetcompanion.models.Item
import com.example.closetcompanion.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private var imageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE = 100
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        imageView = view.findViewById(R.id.imageView)
        button = view.findViewById(R.id.saveButton)

        imageView.setOnClickListener {
            openGallery()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeActivity = requireActivity() as HomePage
        val userr = homeActivity.user
        if (userr != null) {
           Log.d(TAG, "lmao")
        }
        else
            Log.d(TAG, "lmawo")

        //gets the thing for the thang
        val nameText = view.findViewById<EditText>(R.id.nameInput)
        val typeText = view.findViewById<EditText>(R.id.typeInput)
        val sizeText = view.findViewById<EditText>(R.id.sizeInput)
        val colorText = view.findViewById<EditText>(R.id.colorInput)
        val statusText = view.findViewById<EditText>(R.id.statusInput)
        val saveB = view.findViewById<Button>(R.id.saveButton)

        // Get the Firebase Storage and Firestore instances
        val picture = FirebaseStorage.getInstance()
        val db = FirebaseFirestore.getInstance()

        saveB.setOnClickListener {

            // Create a reference to the image file in Firebase Storage
            val timestamp = System.currentTimeMillis()
            val randomString = UUID.randomUUID().toString()
            val imageUrl = "images/$timestamp-$randomString.jpg"
            val imageRef = picture.reference.child(imageUrl)

            // Upload the image file to Firebase Storage
            imageUri?.let { it1 ->
                imageRef.putFile(it1)
                    .addOnSuccessListener {
                        // Get the download URL for the image
                        imageRef.downloadUrl.addOnSuccessListener { uri ->
                            // Create a new document in Firestore with the image URL and the 5 strings

                            val newItem = Item(
                                name = nameText.text.toString(),
                                type = typeText.text.toString(),
                                size = sizeText.text.toString(),
                                color = colorText.text.toString(),
                                status = statusText.text.toString(),
                                image = imageUrl
                            )
                            if (userr != null) {
                                db.collection("Items").document(userr.email_address)
                                    .set(newItem)
                            }

                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error uploading image", e)
                    }
            }
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }
}
