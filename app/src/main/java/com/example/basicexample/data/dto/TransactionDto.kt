package com.example.basicexample.data.dto

data class TransactionDto(
    var companyId: String,
    val personId: String,
    val token: String,
    val date: Long,
    val payId: String,
    val binansId: String,
    val sumRub: String,
    val sumToken: String,
)