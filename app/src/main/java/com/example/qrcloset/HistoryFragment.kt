package com.example.qrcloset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton


class HistoryFragment : Fragment() {

    lateinit var generatedListFragment: GeneratedListFragment
    lateinit var scannedListFragment: ScannedListFragment
    lateinit var button1: MaterialButton
    lateinit var button2: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        button1 = view.findViewById(R.id.scannerButtonList)
        button2 = view.findViewById(R.id.generatorButtonList)


        generatedListFragment = GeneratedListFragment()
        scannedListFragment = ScannedListFragment()
        setCurrentFragment(scannedListFragment)

        button1.setOnClickListener{
            setCurrentFragment(scannedListFragment)
        }
        button2.setOnClickListener {
            setCurrentFragment(generatedListFragment)
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun setCurrentFragment(fragment: Fragment)=
        childFragmentManager.beginTransaction()
            .replace(R.id.fdFragment,fragment)
            .commit()


}