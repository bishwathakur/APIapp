package com.example.apiapp.screens

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.apiapp.Apiservice.RetrofitInstance
import com.example.apiapp.R
import com.example.apiapp.adapter.ImageAdapter
import com.example.apiapp.data.ProductXDet
import com.example.apiapp.databinding.ActivityProductDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //Get product id from intent
        val productId = intent.getIntExtra("PRODUCT_ID", -1)
        if (productId != -1) {
            //Fetch product details from API
            fetchProductDetails(productId)
        } else {
            Toast.makeText(this, "Product ID is missing", Toast.LENGTH_SHORT).show()
        }
//        println("product id: $id")

        //back button
        binding.imageButton.setOnClickListener {
            finish()
        }

    }

    private fun fetchProductDetails(id: Int) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetched")
        progressDialog.show()

        RetrofitInstance.apiInterface.getProdDet(id).enqueue(object : Callback<ProductXDet?> {
            override fun onResponse(call: Call<ProductXDet?>, response: Response<ProductXDet?>) {
                progressDialog.dismiss()
                println("response: ${response.body()}")

                if (response.isSuccessful && response.body() != null) {
                    val product = response.body()!!
                    // Update UI with product details
                    binding.productTitle.text = product.title
                    binding.productDescription.text = product.description
                    binding.productPrice.text = "$${product.price}"
                    binding.productRating.text = "Rating: ⭐ ${product.rating}"
                    binding.productCategory.apply{
                        text = "${product.category}".uppercase()
                    }
                    binding.productAvailability.apply {
                        text = if (product.availabilityStatus == "In Stock") "IN STOCK" else "OUT OF STOCK"
                        setTextColor(
                            if (product.availabilityStatus == "In Stock")
                                ContextCompat.getColor(context, R.color.colorInStock)
                            else
                                ContextCompat.getColor(context, R.color.colorOutOfStock)
                        )
                    }


                    Glide.with(this@ProductDetailActivity)
                        .load(product.thumbnail)
                        .into(binding.productImage)

                    // Set up RecyclerView for additional images
                    binding.productImagesRecyclerView.layoutManager = LinearLayoutManager(this@ProductDetailActivity, LinearLayoutManager.HORIZONTAL, false)
                    binding.productImagesRecyclerView.adapter = ImageAdapter(product.images)
                } else {
                    // Handle error
                    Toast.makeText(this@ProductDetailActivity, "No product found by this ID", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductXDet?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@ProductDetailActivity, "${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}