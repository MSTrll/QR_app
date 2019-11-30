package com.mstrell.qr_app_temp.api_wrapper.models

class Organization(
    val name: String,
    val manager: Manager
)

class Manager(
    val firstName: String,
    val lastName: String
)