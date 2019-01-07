package com.example.laptop.tinkoffnews

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var vText: TextView
    var request: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vText = findViewById<TextView>(R.id.textView)
        vText.setTextColor(0xFFFF0000.toInt())
        vText.setOnClickListener {
            Log.e("tag", "НАЖАТА КНОПКА")
//            val i = Intent(this, SecondActivity::class.java)
//            i.putExtra("tag1", vText.text)
//            startActivityForResult(i, 0)

            val o =
                createRequest("https://api.tinkoff.ru/v1/news")
                    .map { Gson().fromJson(it, PayloadAPI::class.java) }
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

            request = o.subscribe({
                for (item in it.items)
                    Log.w("tag", "text ${item.text}")
            }, {
                Log.e("tag", "Error, but why?", it)
            })
        }
        Log.e("tag", "был запущен onCreate")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            val str = data.getStringExtra("tag2")

            vText.text = str
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        request?.dispose()
        super.onDestroy()
    }
}

class PayloadAPI(
    val items: ArrayList<PayloadItemAPI>
)

class PayloadItemAPI(
    val id: String,
    val name: String,
    val text: String,
    val publicationDate: String,
    val bankInfoTypeId: String

)

/*
"resultCode":"OK","payload":
[
{"id":"10024",
"name":"20122017-tinkoff-bank-x-mgu",
"text":"Тинькофф Банк начинает сотрудничество с кафедрой математических и компьютерных методов анализа мехмата МГУ",
"publicationDate":{"milliseconds":1513767691000},
"bankInfoTypeId":2
},
 */