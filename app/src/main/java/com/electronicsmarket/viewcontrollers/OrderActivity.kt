package com.electronicsmarket.viewcontrollers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electronicsmarket.R
import com.electronicsmarket.data.AddressListHelper
import com.electronicsmarket.data.CartItem

/**
 * The view controller for Order Page, contains the logic for setting the UI elements and updating them
 */
class OrderActivity : AppCompatActivity() {

    private lateinit var recyclerViewer: RecyclerView
    private lateinit var totalCostView: TextView
    private var totalCost: Double = 0.00;
    private var orderList: List<CartItem> = mutableListOf()
    private lateinit var defaultAddress: TextView
    private lateinit var defaultName: TextView
    private lateinit var defaultPhoneNumber:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fill_order_activity)

        defaultAddress = findViewById(R.id.default_address)
        defaultName= findViewById(R.id.default_name)
        defaultPhoneNumber = findViewById(R.id.default_phone_number)

        // Button starts a new Activity for Address Management
        val manageAddressButton: Button = findViewById(R.id.address_manage_screen_button)
        manageAddressButton.setOnClickListener{
            val addressIntent = Intent(this, AddressActivity()::class.java)
            startActivityForResult(addressIntent,3)
        }

        // Set the Default Address information
        val addressListHelper = AddressListHelper.getAddressListHelp()
        val default = addressListHelper.getDefaultAddress()
        if(default != null) {
            defaultAddress.text = default.address
            defaultName.text = default.name
            defaultPhoneNumber.text = default.phoneNumber
        }

        // Set up the total cost TextView
        orderList = intent.getSerializableExtra("OrderList") as ArrayList<CartItem>
        for(item in orderList){
            totalCost += item.product.price * item.quantity
        }
        totalCostView = findViewById(R.id.order_total_view)
        totalCostView.text = totalCost.toString()

        // Set up the adapter and submit the orders for display
        val orderAdapter = OrderAdapter({ Long, Int -> refresh(Long, Int) }, orderList)
        recyclerViewer = findViewById<RecyclerView>(R.id.recyclerViewOrder)
        recyclerViewer.layoutManager = GridLayoutManager(this, 1)
        recyclerViewer.adapter = orderAdapter
        orderAdapter.submitList(orderList)
    }

    /** Updates RecyclerView with new data */
    private fun refresh(id:Long, quantity: Int){
        for(item in orderList){
            if(item.product.id == id){
                item.quantity += quantity
                totalCost += item.product.price * quantity
                totalCostView.text = totalCost.toString()
            }
        }
        recyclerViewer.adapter?.notifyDataSetChanged()
    }

    /** Updates Default Address with new data */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode >= 1) {
            // When the user presses the back button
            if (resultCode == 0) {
                //Update Default Address
                val addressListHelper = AddressListHelper.getAddressListHelp()
                val default = addressListHelper.getDefaultAddress()
                if (default != null) {
                    defaultAddress.text = default.address
                    defaultName.text = default.name
                    defaultPhoneNumber.text = default.phoneNumber
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerViewer.adapter?.notifyDataSetChanged()
    }
}