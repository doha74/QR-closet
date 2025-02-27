package com.example.qrcloset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcloset.Adapter.GeneratedAdapter
import com.example.qrcloset.liste.Generated

class GeneratedListFragment : Fragment() {

    lateinit var recyclerViewGenerated: RecyclerView
    lateinit var adapter: GeneratedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_generated_list, container, false)

        recyclerViewGenerated= view.findViewById(R.id.recyclerViewGenerated)
        createRecyclerView()
        // Inflate the layout for this fragment
        return view
    }

    fun createRecyclerView(){

        recyclerViewGenerated.layoutManager = GridLayoutManager(requireContext(),2)


        adapter = GeneratedAdapter(Generated.listeGenerated, requireContext())

        // Setting the Adapter with the recyclerview
        recyclerViewGenerated.adapter = adapter
    }

}