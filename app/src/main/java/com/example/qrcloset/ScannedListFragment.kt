package com.example.qrcloset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcloset.Adapter.GeneratedAdapter
import com.example.qrcloset.Adapter.ScannedAdapter
import com.example.qrcloset.liste.Generated
import com.example.qrcloset.liste.Scanned


class ScannedListFragment : Fragment() {

    lateinit var recyclerViewScanned: RecyclerView
    lateinit var adapter: ScannedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_scanned_list, container, false)
        recyclerViewScanned= view.findViewById(R.id.recyclerViewScanned)
        createRecyclerView()
        return view
    }

    fun createRecyclerView(){

        recyclerViewScanned.layoutManager = GridLayoutManager(requireContext(),2)


        adapter = ScannedAdapter(Scanned.listeScanned, requireContext())

        // Setting the Adapter with the recyclerview
        recyclerViewScanned.adapter = adapter
    }
}