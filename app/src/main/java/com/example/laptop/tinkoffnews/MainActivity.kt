package com.example.laptop.tinkoffnews

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var vText: TextView
    var request:Disposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vText = findViewById<TextView>(R.id.textView)
        vText.setTextColor(0xFFFF0000.toInt())
        vText.setOnClickListener {
            Log.v("tag", "Кнопка нажата!")
            val i = Intent(this, SecondActivity::class.java)
            i.putExtra("tag1", vText.text)
            startActivityForResult(i, 0)

            val o = Observable.create<String> {

                it.onNext("adsf")
            }.flatMap { Observable.create<String> {} }.zipWith(Observable.create<String> {})
                .map { it.second + it.first }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

            request= o.subscribe({


            }, {


            })

            /*val t = object : Thread() {
                override fun run() {
                    //super.run()
                    this@MainActivity.runOnUiThread {}
                }
            }
            t.start()

            AT(this).execute()*/
        }
        Log.v("tag", "text")
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
        super.onDestroy()
        request?.dispose()
    }
}

/*class AT(val act:MainActivity):AsyncTask<String, Int, String>() {
    override fun doInBackground(vararg params: String?): String {
        return "";
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}
*/