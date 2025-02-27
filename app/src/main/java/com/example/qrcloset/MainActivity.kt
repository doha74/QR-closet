package com.example.qrcloset

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var  qrScannerFragment: QrScannerFragment
    lateinit var qrGeneratorFragment: QrGeneratorFragment
    lateinit var historyFragment: HistoryFragment
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var scannerIcon: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        scannerIcon = findViewById(R.id.fab)

        qrScannerFragment=QrScannerFragment()
        setCurrentFragment(qrScannerFragment)

        qrGeneratorFragment = QrGeneratorFragment()
        historyFragment = HistoryFragment()

        scannerIcon.setOnClickListener{
            setCurrentFragment(qrScannerFragment)
        }

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.generator-> {setCurrentFragment(qrGeneratorFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.history-> {setCurrentFragment(historyFragment)
                    return@setOnItemSelectedListener true
                }
                else -> {return@setOnItemSelectedListener false}
            }
        }



    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}