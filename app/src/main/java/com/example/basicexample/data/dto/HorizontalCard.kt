package com.example.basicexample.data.dto

@kotlinx.serialization.Serializable
data class HorizontalCard (
    val description: String?,
    val filterMap: Map<String, String>,
    val id: Int?,
    val image: Image,
    val internalLink: Boolean?,
    val leftOriented: Boolean?,
    val subTitle: String?,
    val title: String?,
    val url: Boolean?,
        )