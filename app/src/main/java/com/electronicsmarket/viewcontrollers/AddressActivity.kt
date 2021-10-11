package com.electronicsmarket.viewcontrollers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electronicsmarket.R
import com.electronicsmarket.data.Address
import com.electronicsmarket.data.AddressListHelper


class AddressActivity: AppCompatActivity() {

    private lateinit var recyclerViewer: RecyclerView
    var list: ArrayList<Address> = ArrayList()
    private lateinit var addressListHelper: AddressListHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_manage_screen)
        recyclerViewer = findViewById(R.id.recyclerViewAddress)

        addressListHelper = AddressListHelper.getAddressListHelp()
        list.addAll(addressListHelper.getList())

        val addressAdapter = AddressAdapter({ address -> editAddress(address)},{refresh()}, list)
        recyclerViewer.layoutManager = GridLayoutManager(this, 1)
        recyclerViewer.adapter = addressAdapter
        addressAdapter.submitList(list)

        val addAddressButton: Button = findViewById(R.id.add_address_button)
        addAddressButton.setOnClickListener{
            val addAddressIntent = Intent(this, AddEditAddressActivity()::class.java)
            intent.putExtra("edit", false)
            startActivityForResult(addAddressIntent, 2)
        }
    }

    fun refresh(){
        list.clear()
        list.addAll(addressListHelper.getList())
        recyclerViewer.adapter?.notifyDataSetChanged()
    }

    /** Open Add/Edit Address when RecyclerView item is clicked */
    private fun editAddress(address: Address) {
        println("calling Edit")
        val intent = Intent(this, AddEditAddressActivity()::class.java)
        intent.putExtra("edit", true)
        intent.putExtra("address", address)
        startActivityForResult(intent,1)
        refresh()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode >= 1) {
            if (resultCode == Activity.RESULT_OK) {
                //Update List
                refresh()
            }
        }
    }

}