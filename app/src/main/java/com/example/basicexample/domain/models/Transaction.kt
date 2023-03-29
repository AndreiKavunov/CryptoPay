package com.example.basicexample.domain.models

data class Transaction(
    var companyId: String,
    val personId: String,
    val token: String,
    val date: String,
    val payId: String,
    val binansId: String,
    val sumRub: String,
    val sumToken: String,
)
