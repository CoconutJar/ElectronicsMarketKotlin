package com.electronicsmarket.viewcontrollers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.electronicsmarket.R
import com.electronicsmarket.data.Address
import com.electronicsmarket.data.AddressListHelper

/**
 * The view controller for Add/Edit Page, contains the logic for setting the UI elements and saving Addresses
 */
class AddEditAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_edit_address)

        // Checks to see if the users is editing or adding an address
        val isEdit: Boolean = intent.getBooleanExtra("edit", false)
        val editAddress = if(isEdit) {
            // Grab address to edit
            intent.getSerializableExtra("address") as Address
        } else {
            Address("","", "", false)
        }

        val addressListHelper = AddressListHelper.getAddressListHelp()

        val editNameField: EditText = findViewById(R.id.editTextTextPersonName)
        val editPhoneField: EditText = findViewById(R.id.editTextPhone)
        val editPostalAddressField: EditText = findViewById(R.id.editTextTextPostalAddress)
        val setDefault: Switch = findViewById(R.id.defaultSwitch)
        val saveAddressButton: Button = findViewById(R.id.saveAddressButton)

        // Set editfields with edit address information. Will be blank if adding an address
        editNameField.setText(editAddress.name)
        editPhoneField.setText(editAddress.phoneNumber)
        editPostalAddressField.setText(editAddress.address)
        setDefault.isChecked = editAddress.default
        editNameField.hint = "Please enter Name"
        editPhoneField.hint = "Please enter Phone Number"
        editPostalAddressField.hint = "Please enter Postal Address"


        saveAddressButton.setOnClickListener{
            if(editNameField.text.equals("")){
                editNameField.error = "Please enter Name"
            }
            if(editPhoneField.text.equals("")){
                editPhoneField.error = "Please enter Phone Number"
            }
            if(editPostalAddressField.text.equals("")){
                editPostalAddressField.error = "Please enter Postal Address"
            }
            // Check if fields are not empty
            if(!editNameField.text.toString().equals("") &&
                    !editPhoneField.text.toString().equals("") &&
                    !editPostalAddressField.text.toString().equals("")){

                // Remove other default address if this address is the new default
                if(setDefault.isChecked){
                    addressListHelper.removeDefaultAddress()
                }

                // Address to be added
                val address: Address = Address(editPostalAddressField.text.toString(),
                        editNameField.text.toString(),
                        editPhoneField.text.toString(),
                        setDefault.isChecked)

                // IF editing delete the old address first
                if(isEdit) {
                    addressListHelper.deleteAddress(editAddress)
                }
                addressListHelper.addAddress(address)

                // Set return intent to allow Order page to update default address
                val returnIntent = Intent()
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }
}