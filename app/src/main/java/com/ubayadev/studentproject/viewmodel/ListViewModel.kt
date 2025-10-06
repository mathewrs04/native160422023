package com.ubayadev.studentproject.viewmodel

import android.app.Application
import android.util.Log
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
import com.ubayadev.studentproject.util.FileHelper

class ListViewModel(app: Application): AndroidViewModel(app) {
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        loadingLD.value = true // progress bar start muncul
        errorLD.value = false // tidak ada error

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
                studentsLD.value = result as ArrayList<Student>
                loadingLD.value = false

                // simpan juga ke file
                val filehelper = FileHelper(getApplication())
                val jsonstring = Gson().toJson(result)
                filehelper.writeTofFile(jsonstring)
                Log.d("print_file", jsonstring)

                // baca json string dari file
                val hasil = filehelper.readFromFile()
                val listStudent = Gson().fromJson<List<Student>>(hasil, sType)
                Log.d("print_file", listStudent.toString())
            },
            {
                // failed
                errorLD.value = true
                loadingLD.value = false
            }


        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)


    }

//    fun testSaveFile() {
//        val filehelper = FileHelper(getApplication())
//        filehelper.writeTofFile("hello world")
//        val content = filehelper.readFromFile()
//        Log.d("print_file", content)
//        Log.d("print_file", filehelper.getFilePath())
//    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}