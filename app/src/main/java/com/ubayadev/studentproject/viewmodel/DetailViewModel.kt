package com.ubayadev.studentproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayadev.studentproject.model.Student

class DetailViewModel(app:Application): AndroidViewModel(app) {
    val studentLD = MutableLiveData<Student>()
    val TAG = "vollyTag"
    private var queue: RequestQueue? = null

    fun fetch(id:String) {
        // volley ke API
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                it
                // sukses
                val sType = object: TypeToken<List<Student>>() {}.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                val arrStudent = result as ArrayList<Student>
                studentLD.value = arrStudent.find { it.id == id } as Student
            },
            {
                // failed

            }


        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}