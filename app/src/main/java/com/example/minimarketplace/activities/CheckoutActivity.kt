package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minimarketplace.R
import com.example.minimarketplace.data.DataManager
import com.example.minimarketplace.data.ShippingInfo
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CheckoutActivity : AppCompatActivity() {

    private lateinit var tvOrderSummary: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var actvSavedAddresses: AutoCompleteTextView
    private lateinit var etFullName: TextInputEditText
    private lateinit var etPhoneNumber: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var etCity: TextInputEditText
    private lateinit var etPostalCode: TextInputEditText
    private lateinit var btnPlaceOrder: MaterialButton

    private var currentUser: String? = null
    private var selectedShippingInfo: ShippingInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        supportActionBar?.title = "Checkout"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        currentUser = DataManager.getUserSession(this)
        initViews()
        setupOrderSummary()
        setupSavedAddresses()
        setupClickListeners()
        loadDefaultShippingInfo()
    }

    private fun initViews() {
        tvOrderSummary = findViewById(R.id.tvOrderSummary)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        actvSavedAddresses = findViewById(R.id.actvSavedAddresses)
        etFullName = findViewById(R.id.etFullName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etAddress = findViewById(R.id.etAddress)
        etCity = findViewById(R.id.etCity)
        etPostalCode = findViewById(R.id.etPostalCode)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)
    }

    private fun setupOrderSummary() {
        val cartItems = DataManager.getCartItems()
        val summary = StringBuilder()

        cartItems.forEach { item ->
            summary.append("${item.product.name} x${item.quantity}\n")
        }

        tvOrderSummary.text = summary.toString()
        tvTotalAmount.text = "Total: Rp ${String.format("%,.0f", DataManager.getCartTotal())}"
    }

    private fun setupSavedAddresses() {
        currentUser?.let { username ->
            val savedAddresses = DataManager.getAllShippingAddresses(username)
            if (savedAddresses.isNotEmpty()) {
                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, savedAddresses)
                actvSavedAddresses.setAdapter(adapter)

                actvSavedAddresses.setOnItemClickListener { _, _, position, _ ->
                    val shippingInfoList = DataManager.getShippingInfoForUser(username)
                    if (position < shippingInfoList.size) {
                        selectedShippingInfo = shippingInfoList[position]
                        fillFormWithShippingInfo(selectedShippingInfo!!)
                    }
                }
            }
        }
    }

    private fun loadDefaultShippingInfo() {
        currentUser?.let { username ->
            val defaultShipping = DataManager.getDefaultShippingInfo(username)
            defaultShipping?.let {
                selectedShippingInfo = it
                fillFormWithShippingInfo(it)
                actvSavedAddresses.setText("${it.fullName} - ${it.getFormattedAddress()}", false)
            }
        }
    }

    private fun fillFormWithShippingInfo(shippingInfo: ShippingInfo) {
        etFullName.setText(shippingInfo.fullName)
        etPhoneNumber.setText(shippingInfo.phoneNumber)
        etAddress.setText(shippingInfo.address)
        etCity.setText(shippingInfo.city)
        etPostalCode.setText(shippingInfo.postalCode)
    }

    private fun setupClickListeners() {
        btnPlaceOrder.setOnClickListener {
            processOrder()
        }
    }

    private fun processOrder() {
        val fullName = etFullName.text.toString().trim()
        val phoneNumber = etPhoneNumber.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val city = etCity.text.toString().trim()
        val postalCode = etPostalCode.text.toString().trim()

        // Validation
        if (fullName.isEmpty()) {
            etFullName.error = "Nama lengkap tidak boleh kosong"
            return
        }

        if (phoneNumber.isEmpty()) {
            etPhoneNumber.error = "Nomor telepon tidak boleh kosong"
            return
        }

        if (address.isEmpty()) {
            etAddress.error = "Alamat tidak boleh kosong"
            return
        }

        if (city.isEmpty()) {
            etCity.error = "Kota tidak boleh kosong"
            return
        }

        if (postalCode.isEmpty()) {
            etPostalCode.error = "Kode pos tidak boleh kosong"
            return
        }

        // Create shipping address
        val shippingAddress = "$fullName\n$phoneNumber\n$address, $city $postalCode"

        // Create order
        val order = DataManager.createOrder(shippingAddress)

        Toast.makeText(this, "Pesanan berhasil dibuat! ID: ${order.id}", Toast.LENGTH_LONG).show()

        // Navigate back to home
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
