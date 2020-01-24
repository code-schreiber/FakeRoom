package com.toolslab.fakeroom.test

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

class TestApplication : Application()

class TestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader,
        className: String,
        context: Context
    ): Application {
        return super.newApplication(classLoader, TestApplication::class.java.name, context)
    }
}
