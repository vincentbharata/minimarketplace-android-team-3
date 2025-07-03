package com.example.minimarketplace.adapters

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minimarketplace.models.Product
import com.example.minimarketplace.databinding.ItemProductBinding
import com.example.minimarketplace.utils.CartManager

class ProductAdapter(
    private val products: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        with(holder.binding) {
            // Set product data using new professional layout
            ivProductImage.setImageResource(product.imageResId)
            tvProductName.text = product.name
            tvProductDescription.text = product.description
            tvRating.text = product.rating.toString()
            tvProductPrice.text = "$${String.format("%.2f", product.price)}"
            tvReviewCount.text = "(${product.reviewCount} reviews)"

            // Click listeners
            root.setOnClickListener { onProductClick(product) }

            btnAddToCart.setOnClickListener {
                CartManager.addToCart(product)
                animateAddToCart(holder)

                // Notify parent activity to update cart badge
                if (holder.itemView.context is com.example.minimarketplace.activities.HomeActivity) {
                    (holder.itemView.context as com.example.minimarketplace.activities.HomeActivity).updateCartBadgeFromAdapter()
                }
            }
        }
    }

    private fun animateAddToCart(holder: ProductViewHolder) {
        val scaleX = ObjectAnimator.ofFloat(holder.binding.btnAddToCart, "scaleX", 1f, 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(holder.binding.btnAddToCart, "scaleY", 1f, 1.2f, 1f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 200
        animatorSet.start()
    }

    override fun getItemCount() = products.size
}
