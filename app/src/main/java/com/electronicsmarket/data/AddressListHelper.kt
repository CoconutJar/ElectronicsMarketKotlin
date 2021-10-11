package com.electronicsmarket.data

/** This is a singleton that keep track of the addresses */
class AddressListHelper {

    private val savedAddresses = addressList()

    /** Returns list of saved addresses */
    fun getList():List<Address>{
        return savedAddresses
    }

    /** Returns the Default address from the list */
    fun getDefaultAddress(): Address? {
        for(address in savedAddresses){
            if(address.default)
                return address
        }
        return null
    }

    /** Makes the current DefaultAddress not Default anymore */
    fun removeDefaultAddress(){
        for(address in savedAddresses){
            if(address.default)
                 address.default = false
        }
    }

    /** Removes an address from the list */
    fun deleteAddress(delAddress: Address){
        savedAddresses.remove(delAddress)
    }
    /** Adds an address to the list */
    fun addAddress(address: Address){
        savedAddresses.add(address)
    }

    /** Single instance of AddressListHelper class */
    companion object {
        private var INSTANCE: AddressListHelper? = null

        fun getAddressListHelp(): AddressListHelper {
            return synchronized(AddressListHelper::class) {
                val newInstance = INSTANCE ?: AddressListHelper()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}