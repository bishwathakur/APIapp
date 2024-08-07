package com.example.apiapp.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apiapp.R
import com.example.apiapp.data.ProductX

class ProductAdapter(
    private val context: Context,
    private val productList: List<ProductX>,
    private val onItemClick: (ProductX) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productTitle: TextView = itemView.findViewById(R.id.productTitle)
        val productDescription: TextView = itemView.findViewById(R.id.productDescription)

        init {
            itemView.setOnClickListener {
                onItemClick(productList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.apply {
            productTitle.text = product.title
            productDescription.text = product.description
            // Load product image using Glide or any other image loading library
            Glide.with(itemView)
                .load(product.thumbnail)
                .into(productImage)
        }
    }

    override fun getItemCount(): Int = productList.size
}
