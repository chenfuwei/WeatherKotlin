package com.custom.weather.ui.activity

import android.content.Intent
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.custom.weather.R
import com.custom.weather.extesions.ctx
import com.custom.weather.extesions.slideEnter
import com.custom.weather.extesions.slideExit
import com.custom.weather.ui.App
import org.jetbrains.anko.toast

interface ToolbarManager {
    val toolbar : Toolbar

    var toolbarTitle : String
        get() = toolbar.title.toString()
        set(value){
            toolbar.title = value
        }

    fun initToolbar(){
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId)
            {
                R.id.action_settings -> toolbar.ctx.startActivity(Intent(toolbar.ctx, SettingsActivity::class.java))

                else-> App.instance?.toast("Unknown option")
            }
            true
        }
    }

    fun enableHomeAsUp(up :() -> Unit)
    {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() = DrawerArrowDrawable(toolbar.ctx).apply { progress = 1f }

    fun attachToScroll(recycleview : RecyclerView)
    {
        recycleview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }

}