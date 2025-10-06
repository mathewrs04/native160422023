package com.ubayadev.studentproject.model

import com.google.gson.annotations.SerializedName

data class Student (
    var id:String?,
    @SerializedName("student_name")
    var name:String?,
    @SerializedName("birth_of_date")
    var bod:String?,
    var phone:String?,
    @SerializedName("photo_url")
    var photoUrl:String?
)