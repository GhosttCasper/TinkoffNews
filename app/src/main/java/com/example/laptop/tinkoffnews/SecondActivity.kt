package com.example.laptop.tinkoffnews

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        val vEdit = findViewById<EditText>(R.id.editText)

        findViewById<Button>(R.id.button1).setOnClickListener {
            val newStr = vEdit.text.toString()
            val i = Intent()
            i.putExtra("tag2", newStr)
            setResult(0, i)
            finish()
        }

        val str = intent.getStringExtra("tag1")
        vEdit.setText(str)
    }

}