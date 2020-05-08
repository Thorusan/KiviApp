package com.example.kiviapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.example.kiviapp.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy


class ViewPagerActivity : AppCompatActivity() {

    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager2
    @BindView(R.id.tabLayout)
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        viewPager.adapter = ViewPagerAdapter()

        TabLayoutMediator(tabLayout, viewPager,
            TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 ->  tab.text = "Car"
                    1 ->  tab.text = "Motorcycle"

                }


            }).attach()
    }
}
