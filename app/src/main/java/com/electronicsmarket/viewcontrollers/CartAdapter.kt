package com.electronicsmarket.viewcontrollers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electronicsmarket.R
import com.electronicsmarket.data.CartItem
import com.electronicsmarket.data.ShoppingCart

/**
 * Adapter for the [RecyclerView] in [ShoppingCartActivity]. Displays [CartItem] data object.
 */
class CartAdapter(
    private val refresh: () -> (Unit),
    private val updateTotalCost: (Double) -> (Unit),
    private val addToOrder: (Long, Int) -> (Unit),
        private val cartMap: List<CartItem>
): ListAdapter<CartItem, CartAdapter.ItemViewHolder>(RecyclerViewDiffCallbackTwo) {


    class ItemViewHolder(private val refresh: () -> (Unit), private val updateTotalCost: (Double) -> (Unit), private val addToOrder: (Long, Int) -> (Unit), private val view: View) : RecyclerView.ViewHolder(view) {
        // Connect variables to UI elements.
        var cartItemId: Long = 0
        val cartItemCheckBox: CheckBox = view.findViewById(R.id.checkBox)
        val cartItemName: TextView = view.findViewById(R.id.cart_item_name)
        val cartItemCategory: TextView = view.findViewById(R.id.cart_item_category)
        val cartItemCondition: TextView = view.findViewById(R.id.cart_item_condition)
        val cartItemPrice: TextView = view.findViewById(R.id.cart_item_price)
        val cartItemQuantity: TextView = view.findViewById(R.id.item_quantity)
        val cartItemImage: ImageView = view.findViewById(R.id.item_image)

        val increaseButton: Button = view.findViewById(R.id.increase_quantity)
        val decreaseButton: Button = view.findViewById(R.id.decrease_quantity)

        init {
            val cart = ShoppingCart.getShoppingCart()
            increaseButton.setOnClickListener {
                cart.addProduct(cartItemId)
                if(cartItemCheckBox.isChecked){
                    println("here")
                    updateTotalCost(cartItemPrice.text.toString().toDouble())
                    println("added 1 to order")
                    addToOrder(cartItemId, 1)
                }
                refresh()

            }
            decreaseButton.setOnClickListener {
                cart.removeProduct(cartItemId)
                if(cartItemCheckBox.isChecked && cartItemQuantity.text.toString().toInt() > 0){
                    updateTotalCost(0.0- (cartItemPrice.text.toString().toDouble()))
                    addToOrder(cartItemId, -1)
                }
                refresh()
            }
            cartItemCheckBox.setOnClickListener {
                if(cartItemCheckBox.isChecked){
                    updateTotalCost(cartItemPrice.text.toString().toDouble().times(cartItemQuantity.text.toString().toDouble()))
                    addToOrder(cartItemId, cartItemQuantity.text.toString().toInt())
                }
                else{
                    updateTotalCost(0.0- (cartItemPrice.text.toString().toDouble().times(cartItemQuantity.text.toString().toDouble() )))
                    addToOrder(cartItemId, 0-cartItemQuantity.text.toString().toInt())
                }
            }
        }


    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item_view, parent, false)

        return ItemViewHolder(refresh, updateTotalCost, addToOrder, adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = cartMap[position]

        holder.cartItemId = item.product.id
        holder.cartItemName.text = item.product.name
        holder.cartItemCategory.text = item.product.category.toString()
        holder.cartItemCondition.text = item.product.condition.toString()
        holder.cartItemPrice.text = item.product.price.toString()
        holder.cartItemCategory.text = item.product.category.toString()
        holder.cartItemQuantity.text = item.quantity.toString()
        holder.cartItemImage.setImageResource(item.product.images!![0])

    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = cartMap.size

}

object RecyclerViewDiffCallbackTwo : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }

    // Check quantity since it will be changing with the -,+ buttons
    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.quantity == newItem.quantity
    }
}
