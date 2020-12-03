package com.mili.app.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.mili.app.BaseApp
import com.mili.app.simpledi.ActivityModule
import com.mili.app.simpledi.Injector
import com.mili.app.simpledi.ViewModelModule

open class BaseActivity : AppCompatActivity() {
    private val appModule get() = (application as BaseApp).appModule

    private val activityModule by lazy {
        ActivityModule(this,appModule)
    }

    private val viewModule by lazy {
        ViewModelModule(activityModule)
    }

    protected val injector get() = Injector(viewModule)
}