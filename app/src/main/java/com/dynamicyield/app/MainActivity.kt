package com.dynamicyield.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit

fun FragmentActivity.replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
    if (supportFragmentManager.isStateSaved || supportFragmentManager.isDestroyed) return
    if (this !is MainActivity) return

    supportFragmentManager.commit {
        replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
        if (addToBackStack) {
            addToBackStack(fragment.javaClass.simpleName)
        }
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            replaceFragment(SelectionFragment.newInstance(), addToBackStack = false)
        }
    }

}