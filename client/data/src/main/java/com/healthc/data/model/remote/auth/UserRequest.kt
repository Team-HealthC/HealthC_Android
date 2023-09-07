package com.healthc.data.model.remote.auth
data class UserRequest (
    val name: String,
    val allergies: List<String>,
){
    constructor(): this("", emptyList())
}