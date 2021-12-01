package com.example.simpleweatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.simpleweatherapp.R

class HomeActivity : AppCompatActivity() {
    private var addIconToolbar: AppCompatImageView? = null
    private var backBtn: ImageView? = null
    private var title: TextView? = null
    private var doneBtn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        handleBackBtnAction()
        handleDoneBtnAction()
    }

    private fun handleDoneBtnAction() {
        doneBtn?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun handleBackBtnAction() {
        backBtn?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun init() {
        addIconToolbar = findViewById(R.id.add_icon_toolbar)
        backBtn = findViewById(R.id.back_btn)
        title = findViewById(R.id.page_title)
        doneBtn = findViewById(R.id.done_btn)
    }

    fun setTitle(titleString: String) {
        title?.text = titleString
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

    fun hideDoneBtn() {
        doneBtn?.visibility = View.GONE
    }

    fun showDoneBtn() {
        doneBtn?.visibility = View.VISIBLE
    }

    fun hideBackBtn() {
        backBtn?.visibility = View.GONE
    }

    fun showBackBtn() {
        backBtn?.visibility = View.VISIBLE
    }

}