package com.example.apiapp.screens

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiapp.Apiservice.ProductResponse
import com.example.apiapp.Apiservice.RetrofitInstance
import com.example.apiapp.R
import com.example.apiapp.adapter.ProductAdapter
import com.example.apiapp.data.ProductX
import com.example.apiapp.databinding.ActivityProductListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Set up RecyclerView
        setupRecyclerView()

        // Fetch products from the API
        fetchProducts()
    }

    private fun setupRecyclerView() {
        // Set up LayoutManager
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchProducts() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetched")
        progressDialog.show()

        RetrofitInstance.apiInterface.getProducts().enqueue(object : Callback<ProductResponse?> {
            override fun onResponse(
                call: Call<ProductResponse?>,
                response: Response<ProductResponse?>
            ) {
                progressDialog.dismiss()
                println("response: ${response.body()}")

                if (response.isSuccessful && response.body() != null) {
                    val products = response.body()!!.products.map {
                        ProductX(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            thumbnail = it.thumbnail
                        )
                    }
                    val productAdapter = ProductAdapter(this@ProductListActivity, products) { product ->
                        val intent = Intent(this@ProductListActivity, ProductDetailActivity::class.java)
                        intent.putExtra("PRODUCT_ID", product.id)
//                        println("product id: ${product.id}")
//                        println("product: ${product}")
                        startActivity(intent)
                    }
                    binding.productRecyclerView.adapter = productAdapter
                } else {
                    Toast.makeText(this@ProductListActivity, "No products found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse?>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@ProductListActivity, "${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
