package com.example.basicexample.domain.models

data class CompanyInfo(
    var id: String,
    val name_org: String,
    val phone: String,
    val email: String,
    val address: String,
    val inn: String,
    val bic: String,
    val bank_name: String,
    val checking_account: String,
    val sum: Int,
    val action: Boolean,
    val payId: String,
    val binansId: String,
)
