package com.electronicsmarket.data

import android.content.Context
import android.widget.Toast
import kotlin.math.min

/**
 * The handler first caches a list, then filters by category, by search term, and sorts.
 * The main functions are arranged in this order, and every step's result is saved for
 * easier backtracking.
 */
class ProductListHandler {
    /** PFilter/Sort parameters */
    private var cacheSize = 5
    private var cachedProductList: List<Product>

    private var filterCategory = 1
    private var filteredList: List<Product>
    private val categoryList = enumValues<ProductCategory>()

    private var searchQuery = ""
    private var searchedList: List<Product>

    private var sortBy = "Name"
    private var ascendingSort = true

    init {
        cachedProductList = getProductRecords(cacheSize)
        filteredList = cachedProductList
        searchedList = cachedProductList
    }

    /** Public methods for getting the current list or applying filter/sort and returning a list */
    fun getProducts(): List<Product> {
        return searchedList
    }

    fun sortProducts(property: String): List<Product> {
        ascendingSort = if (property != sortBy) {
            true
        } else {
            !ascendingSort
        }
        sortBy = property
        return sortProductList()
    }

    fun searchProducts(query: String): List<Product> {
        searchQuery = query
        return searchProductList()
    }

    fun filterProducts(pos: Int): List<Product> {
        filterCategory = pos
        return filterProductList()
    }

    fun loadMoreProduct(context:Context): List<Product> {
        if (cacheSize < productSize()) {
            cacheSize = min(cacheSize + 5, productSize())
            cachedProductList = getProductRecords(cacheSize)
        } else {
            Toast.makeText(context,"No more items to load", Toast.LENGTH_SHORT).show()
        }
        return filterProductList()
    }
    /** Public methods end here */

    /** Private methods for processing the list */
    private fun filterProductList(): List<Product> {
        filteredList = if (categoryList[filterCategory] == ProductCategory.CLEAR) {
            cachedProductList
        } else {
            val tempList = mutableListOf<Product>()
            for (p in cachedProductList) {
                if (p.category == categoryList[filterCategory]) {
                    tempList.add(p)
                }
            }
            tempList
        }
        return searchProductList()
    }

    private fun searchProductList(): List<Product> {
        val tempList = mutableListOf<Product>()
        val regex = Regex(".*((?i)"+searchQuery+")+.*")

        searchedList = if (searchQuery.isNullOrEmpty()) {
            filteredList
        } else {
            for (product in filteredList) {
                /**
                 * The search matches the query with product names, if you want to the search to
                 * include category and condition replace the if with the following:
                 * if (regex.matches(product.name) || regex.matches(product.category.printableName)
                 * || regex.matches(product.condition.printableName))
                 */
                if (regex.matches(product.name)) {
                    tempList.add(product)
                }
            }
            tempList
        }
        return sortProductList()
    }

    private fun sortProductList(): List<Product> {
        if (ascendingSort) {
            searchedList = when (sortBy) {
                "Name" -> searchedList.sortedBy(Product::name)
                "Category" -> searchedList.sortedBy(Product::category)
                "Price" -> searchedList.sortedBy(Product::price)
                "Condition" -> searchedList.sortedBy(Product::condition)
                else -> searchedList
            }
        } else {
            searchedList = when (sortBy) {
                "Name" -> searchedList.sortedByDescending(Product::name)
                "Category" -> searchedList.sortedByDescending(Product::category)
                "Price" -> searchedList.sortedByDescending(Product::price)
                "Condition" -> searchedList.sortedByDescending(Product::condition)
                else -> searchedList
            }
        }
        return searchedList
    }
}