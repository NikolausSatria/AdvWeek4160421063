package com.example.advweek4160421063.model

import com.google.gson.annotations.SerializedName

data class Student(
    val id:String?,

    @SerializedName("student_name")
    val name:String?,

    @SerializedName("birth_of_date")
    val dob:String?,
    val phone:String?,

    @SerializedName("photo_url")
    val photoUrl:String?
)
