package com.example.laptop.tinkoffnews

import android.app.Application
import io.realm.Realm

class App : Application() {
    fun OnCreate() {
        super.onCreate()

        Realm.init(this)

//        getSharedPreferences("name",0).edit().putString("zzz","xx").apply()
//        getSharedPreferences("name",0).getString("zzz","")
    }
}