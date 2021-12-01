package com.example.simpleweatherapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.simpleweatherapp.R

class HomeActivity : AppCompatActivity() {
    private var addIconToolbar: AppCompatImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addIconToolbar = findViewById(R.id.add_icon_toolbar)
    }

    fun getAddIconToolbar(): AppCompatImageView? {
        return addIconToolbar
    }

    fun hideAddIconToolbar() {
        addIconToolbar?.visibility = View.GONE
    }

    fun showAddIconToolbar() {
        addIconToolbar?.visibility = View.VISIBLE
    }

}