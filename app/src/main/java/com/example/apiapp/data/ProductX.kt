package com.example.apiapp.data

data class ProductX(
    val description: String,
    val id: Int,
    val thumbnail: String,
    val title: String
)

data class ProductXDet(
    val availabilityStatus: String,
    val category: String,
    val description: String,
    val dimensions: DimensionsX,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val meta: MetaX,
    val minimumOrderQuantity: Int,
    val price: Double,
    val rating: Double,
    val returnPolicy: String,
    val reviews: List<ReviewX>,
    val shippingInformation: String,
    val sku: String,
    val stock: Int,
    val tags: List<String>,
    val thumbnail: String,
    val title: String,
)