package com.electronicsmarket.viewcontrollers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electronicsmarket.R
import com.electronicsmarket.data.Address
import com.electronicsmarket.data.AddressListHelper


class AddressAdapter (private val editAddress: (Address) -> (Unit), private val refresh: () -> (Unit),
    private val addressList: List<Address>
): ListAdapter<Address, AddressAdapter.ItemViewHolder>(RecyclerViewDiffCallbackAddress) {

    class ItemViewHolder(private val editAddress: (Address) -> (Unit), private val refresh: () -> (Unit), private val view: View) : RecyclerView.ViewHolder(view) {

        val addressItemName: TextView = view.findViewById(R.id.name_text_view)
        val addressItemAddress: TextView = view.findViewById(R.id.address_text_view)
        val addressItemPhoneNumber: TextView = view.findViewById(R.id.phone_number_view)
        val addressItemDefault: TextView = view.findViewById(R.id.default_address_view)

        val addressEditButton: ImageButton = view.findViewById(R.id.edit_address_button)
        val addressDeleteButton: ImageButton = view.findViewById(R.id.delete_address_button)


        init{

            // Edit the selected address
            addressEditButton.setOnClickListener{
                val default:Boolean = addressItemDefault.text.toString().equals("Default")
                val address:Address = Address(addressItemAddress.text.toString(),
                        addressItemName.text.toString(),
                        addressItemPhoneNumber.text.toString(),
                        default)
                editAddress(address)
            }

            // Delete the selected address
            addressDeleteButton.setOnClickListener{
                val default:Boolean = addressItemDefault.text.toString().equals("Default")
                val address:Address = Address(addressItemAddress.text.toString(),
                        addressItemName.text.toString(),
                        addressItemPhoneNumber.text.toString(),
                        default)
                val addressListHelper = AddressListHelper.getAddressListHelp()
                addressListHelper.deleteAddress(address)
                refresh()
            }
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapter.ItemViewHolder {
        // create a new view

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.address_item_view, parent, false)

        return AddressAdapter.ItemViewHolder(editAddress, refresh, adapterLayout)
    }

    override fun onBindViewHolder(holder: AddressAdapter.ItemViewHolder, position: Int) {
        val item = addressList[position]

        holder.addressItemName.text = item.name
        holder.addressItemPhoneNumber.text = item.phoneNumber
        holder.addressItemAddress.text = item.address
        holder.addressItemDefault.text = if(item.default) "Default" else ""

    }
}

object RecyclerViewDiffCallbackAddress : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.address == newItem.address &&
                oldItem.name == newItem.name &&
                oldItem.phoneNumber == newItem.phoneNumber &&
                oldItem.default == newItem.default
    }
}