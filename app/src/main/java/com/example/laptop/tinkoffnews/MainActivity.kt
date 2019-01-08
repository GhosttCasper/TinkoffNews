package com.example.laptop.tinkoffnews

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var vText: TextView
    lateinit var vList: LinearLayout
    lateinit var vListView: ListView
    var request: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        vList = findViewById<LinearLayout>(R.id.act1_list)
        vListView = findViewById<ListView>(R.id.act1_listView)

        val o =
            createRequest("https://api.tinkoff.ru/v1/news")
                .map { Gson().fromJson(it, PayloadAPI::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        request = o.subscribe({
            showListView(it.payload)
//            showLinearLayout(it.payload)

            //                for (item in it.payload)
//                    Log.w("tag", "text ${item.text}")
        }, {
            Log.e("tag", "Error, but why?", it)
        })
    }

    fun showLinearLayout(payloadList: ArrayList<PayloadItemAPI>) {
        val inflater = layoutInflater
        for (f in payloadList) {
            val view = inflater.inflate(R.layout.list_item, vList, false)
            val vTitle = view.findViewById<TextView>(R.id.item_title)
            vTitle.text = f.text
            vList.addView(view)
        }
    }

    fun showListView(payloadList: ArrayList<PayloadItemAPI>) {
        vListView.adapter = Adapter(payloadList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            val str = data.getStringExtra("tag2")

            vText.text = str
        }
    }

    override fun onDestroy() {
        request?.dispose()
        super.onDestroy()
    }
}

class PayloadAPI(
    val payload: ArrayList<PayloadItemAPI>
)

class PayloadItemAPI(
    val id: String,
    val name: String,
    val text: String,
    val publicationDate: BeginObject,
    val bankInfoTypeId: String
)

class BeginObject(
    val milliseconds: String
)

class Adapter(val payload: ArrayList<PayloadItemAPI>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = convertView ?: inflater.inflate(R.layout.list_item, parent, false)
        val vTitle = view.findViewById<TextView>(R.id.item_title)
        val item = getItem(position) as PayloadItemAPI
        vTitle.text = item.text
        return view
    }

    override fun getItem(position: Int): Any {
        return payload[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return payload.size
    }

}

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