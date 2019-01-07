package com.example.laptop.tinkoffnews

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SecondActivity : Activity() {
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