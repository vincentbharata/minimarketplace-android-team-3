package com.example.minimarketplace.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minimarketplace.R
import com.example.minimarketplace.data.CartItem

class CartAdapter(
    private var items: MutableList<CartItem>,
    private val onQuantityChanged: (String, Int) -> Unit,
    private val onItemRemoved: (String) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProduct: ImageView = view.findViewById(R.id.ivProduct)
        val tvProductName: TextView = view.findViewById(R.id.tvProductName)
        val tvProductPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
        val btnDecrease: ImageButton = view.findViewById(R.id.btnDecrease)
        val btnIncrease: ImageButton = view.findViewById(R.id.btnIncrease)
        val btnRemove: ImageButton = view.findViewById(R.id.btnRemove)
        val tvTotalPrice: TextView = view.findViewById(R.id.tvTotalPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = items[position]
        val product = cartItem.product

        holder.tvProductName.text = product.name
        holder.tvProductPrice.text = product.getFormattedPrice()
        holder.tvQuantity.text = cartItem.quantity.toString()
        holder.tvTotalPrice.text = cartItem.getFormattedTotalPrice()

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(holder.ivProduct)

        holder.btnIncrease.setOnClickListener {
            onQuantityChanged(product.id, cartItem.quantity + 1)
        }

        holder.btnDecrease.setOnClickListener {
            if (cartItem.quantity > 1) {
                onQuantityChanged(product.id, cartItem.quantity - 1)
            }
        }

        holder.btnRemove.setOnClickListener {
            onItemRemoved(product.id)
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<CartItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
