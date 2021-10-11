package com.electronicsmarket.viewcontrollers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electronicsmarket.data.Product
import com.electronicsmarket.data.ProductCategory
import com.electronicsmarket.data.ProductListHandler
import com.electronicsmarket.data.ShoppingCart
import com.electronicsmarket.R

const val PRODUCT_ID = "product id"

/**
 * The view controller for mainActivity, contains the logic for setting the UI elements
 * All data manipulation is performed by ProductListHandler
 * The order of the functions follows the placement of the UI element going row by row
 */
class ProductListActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val productHandler =  ProductListHandler()
    private val mutableProductList = mutableListOf<Product>()
    private var tempProductList = productHandler.getProducts()
    private var tileView = false
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)

        setSearchBar()
        setFilterMenu()
        setViewSwitch()
        setSortLabels()
        setRecyclerView()
        setLoadMore()
        setCheckCart()
    }

    private fun setSearchBar() {
        val searchBar: SearchView = findViewById(R.id.search_bar)
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return validSearch(query)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return validSearch(newText)
            }
        })
    }

    /** If search term is valid, calls product handler to perform search */
    private fun validSearch(query: String?): Boolean {
        if (query is String) {
            tempProductList = productHandler.searchProducts(query)
            notifyAdapters()
            return true
        }
        return false
    }

    private fun setFilterMenu() {
        val filterList = enumValues<ProductCategory>()
        val filterSpinner: Spinner = findViewById(R.id.spinner)
        filterSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
            filterList.map { it.printableName })
        filterSpinner.onItemSelectedListener = this
    }

    /** Override method for spinner (filter drop-down menu) */
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        tempProductList = productHandler.filterProducts(pos)
        notifyAdapters()
    }

    /** Override method for spinner (filter drop-down menu) */
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Do nothing
    }

    private fun setViewSwitch() {
        val listTileSwitch: ImageButton = findViewById(R.id.listTileSwitch)
        listTileSwitch.setOnClickListener {
            tileView = !tileView
            setButtonIcon(listTileSwitch)
            setRecyclerView()
        }
    }

    private fun setButtonIcon(button: ImageButton) {
        if (tileView) {
            button.setImageResource(R.drawable.list_icon)
        } else {
            button.setImageResource(R.drawable.tile_icon)
        }
    }

    private fun setSortLabels() {
        val nameLabel: TextView = findViewById(R.id.nameLabel)
        val categoryLabel: TextView = findViewById(R.id.categoryLabel)
        val priceLabel: TextView = findViewById(R.id.priceLabel)
        val conditionLabel: TextView = findViewById(R.id.conditionLabel)

        nameLabel.setOnClickListener { sortItems("Name") }
        categoryLabel.setOnClickListener { sortItems("Category") }
        priceLabel.setOnClickListener { sortItems("Price") }
        conditionLabel.setOnClickListener { sortItems("Condition") }
    }

    private fun sortItems(property: String) {
        tempProductList = productHandler.sortProducts(property)
        notifyAdapters()
    }

    private fun setRecyclerView(){
        if (tileView) {
            val productAdapter = ProductTileAdapter { product -> adapterOnClick(product) }
            recyclerView.layoutManager = GridLayoutManager(this,3)
            recyclerView.adapter = productAdapter
            productAdapter.submitList(mutableProductList)

        } else {
            val productAdapter = ProductListAdapter { product -> adapterOnClick(product) }
            recyclerView.layoutManager = GridLayoutManager(this,1)
            recyclerView.adapter = productAdapter
            productAdapter.submitList(mutableProductList)
        }
    }

    /** Open ProductDetailActivity when RecyclerView item is clicked */
    private fun adapterOnClick(product: Product) {
        val intent = Intent(this, ProductDetailActivity()::class.java)
        intent.putExtra(PRODUCT_ID, product.id)
        startActivity(intent)
    }

    /** Updates RecyclerView with new data */
    private fun notifyAdapters() {
        mutableProductList.clear()
        mutableProductList.addAll(tempProductList)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setLoadMore() {
        val loadMoreButton: Button = findViewById(R.id.load_more_button)
        loadMoreButton.setOnClickListener {
            tempProductList = productHandler.loadMoreProduct(this)
            notifyAdapters()
        }
    }

    /** Bonus feature for checking shopping cart*/
    private fun setCheckCart() {
        val checkCartButton: ImageButton = findViewById(R.id.check_cart_button)
        checkCartButton.setOnClickListener {
            val cart = ShoppingCart.getShoppingCart()
            Toast.makeText(this, cart.printCart(), Toast.LENGTH_SHORT).show()

            val intent2 = Intent(this, ShoppingCartActivity()::class.java)

            startActivity(intent2)


        }
    }
}