package com.chaldrac.facebookrecipientapp.entities

import com.chaldrac.facebookrecipientapp.db.ReceipesDatabase
import com.dbflow5.annotation.Column
import com.dbflow5.annotation.PrimaryKey
import com.dbflow5.annotation.Table
import com.dbflow5.database.DatabaseWrapper
import com.dbflow5.structure.BaseModel
import com.google.gson.annotations.SerializedName

@Table(database = ReceipesDatabase::class)
open class Recipe : BaseModel() {

    override fun exists(wrapper: DatabaseWrapper): Boolean {
        return super.exists(wrapper)
    }

    @SerializedName("recipe_id")
    @PrimaryKey
    var recipeId: String? = null

    @Column
    var title: String? = null

    @SerializedName("image_url")
    @Column
    var imageUrl: String? = null

    @SerializedName("source_url")
    @Column
    var sourceUrl: String? = null

    @Column
    var isFavorie: Boolean = false

    override fun equals(other: Any?): Boolean {
        var equal = false
        if (other is Recipe) {
            val recipe = other as Recipe?
            equal = this.recipeId == recipe!!.recipeId
        }
        return equal
    }

    override fun hashCode(): Int {
        var result = recipeId?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (sourceUrl?.hashCode() ?: 0)
        result = 31 * result + isFavorie.hashCode()
        return result
    }

    /* fun exists(): Boolean {

     }*/
}
