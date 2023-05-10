package com.example.closetcompanion.fragments

import AddClosetFragment
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.closetcompanion.R
import com.example.closetcompanion.data.Closet
import com.example.closetcompanion.data.ClosetDao
import com.example.closetcompanion.data.ClosetDatabase
import com.example.closetcompanion.data.ClothesDao
import com.example.closetcompanion.fragments.RecyclerView.ClosetAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var closetDatabase: ClosetDatabase
private lateinit var closetDao: ClosetDao
private lateinit var clothesDao: ClothesDao
private lateinit var recyclerView: RecyclerView
//private lateinit var adapter: ClosetsAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [closetsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class closetsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_closets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize database and DAO
        closetDatabase = ClosetDatabase.getDatabase(requireContext())
        clothesDao = closetDatabase.clothesDao()
        closetDao = closetDatabase.closetDao()

        recyclerView = view.findViewById(R.id.closet_recycler_view)

        val thingamajig = Closet(0,"Joe mam", emptyList())
        val thingamajig2 = Closet(1,"hello", emptyList())
        val thingamajig3 = Closet(2,"testing", emptyList())

        var closetList = mutableListOf<Closet>()
        GlobalScope.launch {
            closetList.addAll(closetDao.getAllClosets())
            withContext(Dispatchers.Main) {
                recyclerView.adapter = ClosetAdapter(closetList, requireContext())
            }
        }

        // Set click listener for add closet button
        val addClosetButton = view.findViewById<FloatingActionButton>(R.id.add_closet_button)
        addClosetButton.setOnClickListener {
            // Create an instance of the new fragment
            val addFragment = AddClosetFragment()

            // Get the FragmentManager
            val fragmentManager = requireActivity().supportFragmentManager

            // Replace the current fragment with the new fragment
            fragmentManager.beginTransaction()
                .replace(R.id.home_page_fragment_container, addFragment)
                .commit()
            }
        }


    /*private fun showAddClosetDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("New Closet Name")

        val editText = EditText(requireContext())
        builder.setView(editText)

        builder.setPositiveButton("Ok") { _, _ ->
            val newClosetName = editText.text.toString()
            if (newClosetName.isNotEmpty()) {
                val newCloset = Closet(name = newClosetName, outfitIds = emptyList())
                GlobalScope.launch {
                    closetDao.insertCloset(newCloset)
                    val clothesList = closetDao.getAllClosets()
                    clothesList.forEach { clothes ->
                        println("Clothes id: ${clothes.id}")
                        println("Clothes name: ${clothes.name}")
                    }
                   print(newCloset)
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.create().show()
    }*/

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment closetsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            closetsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}