package com.alv1n.barnewsalvin10119187

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import com.alv1n.barnewsalvin10119187.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import com.alv1n.barnewsalvin10119187.ui.donasi.DonasiFragment
import com.alv1n.barnewsalvin10119187.ui.home.HomeFragment
import com.alv1n.barnewsalvin10119187.ui.tentang.TentangFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnNavigationItemSelectedListener (mOnNavigationItemSelectedListener)

        val moveToDonation = intent.getBooleanExtra("moveToFragmentDonation", false)
        val moveToTunadaksa = intent.getBooleanExtra("moveToFragmentTunadaksa", false)
        val moveToAbout = intent.getBooleanExtra("moveToFragmentAbout", false)

        when {
            moveToDonation -> {
                binding.bottomNav.menu.getItem(3).isChecked = true
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.home_frame,
                        DonasiFragment()
                    ).commit()
            }

            moveToAbout -> {
                binding.bottomNav.menu.getItem(5).isChecked = true
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.home_frame,
                        TentangFragment()
                    ).commit()
            }

            else -> {
                supportFragmentManager.beginTransaction().replace(R.id.home_frame, HomeFragment()).commit()
            }
        }

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        val fragmentCheck = fun(fragmentId: Int, fragment: Fragment) {
            if (currentId != fragmentId) {
                supportFragmentManager.beginTransaction().replace(R.id.home_frame, fragment).commit()
            }
        }

        when(it.itemId) {
            R.id.navigation_home -> fragmentCheck(R.id.navigation_home, HomeFragment())
            R.id.navigation_donasi -> fragmentCheck(R.id.navigation_donasi, DonasiFragment())
            R.id.navigation_tentang -> fragmentCheck(R.id.navigation_tentang, TentangFragment())
        }

        currentId =it.itemId
        true
    }
}