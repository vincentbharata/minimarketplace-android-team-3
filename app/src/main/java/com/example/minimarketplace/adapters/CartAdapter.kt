package com.example.minimarketplace.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minimarketplace.models.CartItem
import com.example.minimarketplace.databinding.ItemCartBinding

class CartAdapter(
    private var cartItems: MutableList<CartItem>,
    private val onQuantityChanged: (CartItem, Int) -> Unit,
    private val onRemoveItem: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        with(holder.binding) {
            ivProduct.setImageResource(cartItem.product.imageResId)
            tvProductName.text = cartItem.product.name
            tvPrice.text = "$${cartItem.product.price}"
            tvQuantity.text = cartItem.quantity.toString()
            tvItemTotal.text = "$${String.format("%.2f", cartItem.product.price * cartItem.quantity)}"

            // Quantity controls
            btnDecrease.setOnClickListener {
                if (cartItem.quantity > 1) {
                    onQuantityChanged(cartItem, cartItem.quantity - 1)
                }
            }

            btnIncrease.setOnClickListener {
                onQuantityChanged(cartItem, cartItem.quantity + 1)
            }

            // Remove item
            btnRemove.setOnClickListener {
                onRemoveItem(cartItem)
            }
        }
    }

    override fun getItemCount() = cartItems.size

    fun updateItems(newItems: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(newItems)
        notifyDataSetChanged()
    }
}
