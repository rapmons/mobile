package com.midterm.english_app.model

class FavouriteModel {
    data class Item(val id: Int? = null, val accID: String? = null, val vocabulary: String = "", val vi: String = "", val description: String = "", var synced: String = "")
}