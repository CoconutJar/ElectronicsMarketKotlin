package com.electronicsmarket.data

import com.electronicsmarket.R

/** Get the first "recordsNumber" products. */
fun getProductRecords(recordNumber: Int): List<Product> {
    if (recordNumber <= productSize()) {
        return productList().subList(0, recordNumber)
    }
    return productList()
}

/** Size of the the total product list */
fun productSize(): Int {
    return 15
}

/** The full list of products */
fun productList(): List<Product> {
    val noDescription = "The seller didn't add any description."
    return listOf(
        Product(
            id = 1,
            name = "ASUS Laptop",
            images = listOf(R.drawable.asus_1, R.drawable.asus_2, R.drawable.asus_3),
            price = 999.99F,
            category = ProductCategory.COMPUTER,
            condition = ProductCondition.OLD,
            description = ("Its Intel Core i5-10300H processor delivers powerful processing " +
                "performance while 8GB of RAM helps run graphic-heavy games smoothly.")
        ),
        Product(
            id = 2,
            name = "Acer Laptop",
            images = listOf(R.drawable.acer_1, R.drawable.acer_2, R.drawable.acer_3),
            price = 199.99F,
            category = ProductCategory.COMPUTER,
            condition = ProductCondition.NEW,
            description = ("Handle your everyday tasks with ease by using this Acer " +
                "11.6\" Chromebook. Built for avid internet users, it runs on Chrome" +
                " OS and comes with various Google apps to make your life easy.")
        ),
        Product(
            id = 3,
            name = "Apple AirPods",
            images = listOf(R.drawable.airpod_1, R.drawable.airpod_2, R.drawable.airpod_3),
            price = 159.99F,
            category = ProductCategory.HEADPHONE,
            condition = ProductCondition.OLD,
            description = "AirPods Pro have been designed to deliver Active Noise Cancellation."
        ),
        Product(
            id = 4,
            name = "Apple EarPods",
            images = listOf(R.drawable.ear_pods_1, R.drawable.ear_pods_2, R.drawable.ear_pods_3 ),
            price = 26.0F,
            category = ProductCategory.HEADPHONE,
            condition = ProductCondition.USED,
            description = noDescription
        ),
        Product(
            id = 5,
            name = "Bose Headphone",
            images = listOf(R.drawable.bose_headphone_1, R.drawable.bose_headphone_2,
                R.drawable.bose_headphone_3),
            price = 269.0F,
            category = ProductCategory.HEADPHONE,
            condition = ProductCondition.OLD,
            description = noDescription
        ),
        Product(
            id = 6,
            name = "Chrome Book",
            images = listOf(R.drawable.chromebook_1, R.drawable.chromebook_2,
                R.drawable.chromebook_3),
            price = 369.0F,
            category = ProductCategory.COMPUTER,
            condition = ProductCondition.OLD,
            description = noDescription
        ),
        Product(
            id = 7,
            name = "Dell PC",
            images = listOf(R.drawable.dell_pc_1, R.drawable.dell_pc_2,
                R.drawable.dell_pc_3),
            price = 1500.0F,
            category = ProductCategory.COMPUTER,
            condition = ProductCondition.NEW,
            description = noDescription
        ),
        Product(
            id = 8,
            name = "Google Pixel",
            images = listOf(R.drawable.google_pixel_1, R.drawable.google_pixel_2,
                R.drawable.google_pixel_3 ),
            price = 626.0F,
            category = ProductCategory.PHONE,
            condition = ProductCondition.USED,
            description = noDescription
        ),
        Product(
            id = 9,
            name = "Macbook",
            images = listOf(R.drawable.macbook_1, R.drawable.macbook_2, R.drawable.macbook_3 ),
            price = 6026.66F,
            category = ProductCategory.COMPUTER,
            condition = ProductCondition.OLD,
            description = noDescription
        ),
        Product(
            id = 10,
            name = "Microsoft Surface",
            images = listOf(R.drawable.microsoft_surface_1, R.drawable.microsoft_surface_2,
                R.drawable.microsoft_surface_3 ),
            price = 1096.66F,
            category = ProductCategory.TABLET,
            condition = ProductCondition.OLD,
            description = noDescription
        ),
        Product(
            id = 11,
            name = "Samsung Galaxy",
            images = listOf(R.drawable.samsung_galaxy_1, R.drawable.samsung_galaxy_2,
                R.drawable.samsung_galaxy_3 ),
            price = 626.0F,
            category = ProductCategory.PHONE,
            condition = ProductCondition.OLD,
            description = noDescription
        ),
        Product(
            id = 12,
            name = "Samsung Tablet",
            images = listOf(R.drawable.samsung_tablet_1, R.drawable.samsung_tablet_2,
                R.drawable.samsung_tablet_3 ),
            price = 4426.66F,
            category = ProductCategory.TABLET,
            condition = ProductCondition.USED,
            description = noDescription
        ),
        Product(
            id = 13,
            name = "Sony Headphone",
            images = listOf(R.drawable.sony_headphone_1, R.drawable.sony_headphone_2,
                R.drawable.sony_headphone_3 ),
            price = 296.66F,
            category = ProductCategory.HEADPHONE,
            condition = ProductCondition.NEW,
            description = noDescription
        ),
        Product(
            id = 14,
            name = "iPad Pro",
            images = listOf(R.drawable.ipad_pro_1, R.drawable.ipad_pro_2, R.drawable.ipad_pro_3 ),
            price = 1626.0F,
            category = ProductCategory.TABLET,
            condition = ProductCondition.USED,
            description = noDescription
        ),
        Product(
            id = 15,
            name = "iPhone 12",
            images = listOf(R.drawable.iphone_12_1, R.drawable.iphone_12_2,
                R.drawable.iphone_12_3 ),
            price = 1026.0F,
            category = ProductCategory.PHONE,
            condition = ProductCondition.OLD,
            description = noDescription
        ),
    )
}