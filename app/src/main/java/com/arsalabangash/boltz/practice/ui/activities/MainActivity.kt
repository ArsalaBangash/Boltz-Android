package com.arsalabangash.boltz.practice.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arsalabangash.boltz.practice.R
import com.arsalabangash.boltz.practice.ui.fragments.CustomizeFragment
import com.arsalabangash.boltz.practice.ui.fragments.StartPracticeFragmentImpl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_customize, CustomizeFragment.newInstance(), "Customize Fragment")
                .replace(R.id.fragment_container_startpractice, StartPracticeFragmentImpl.newInstance(), "StartPractice Fragment")
                .commit()
    }
}
