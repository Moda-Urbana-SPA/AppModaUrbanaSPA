package com.example.modaurbanaspa.repository
import android.content.Context
import com.example.modaurbanaspa.model.Product
import com.example.modaurbanaspa.model.Variant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray

class LocalCatalogRepository(private val context: Context){
    suspend fun getAll(): List<Product> = withContext(Dispatchers.IO) {
        runCatching {
            val json = context.assets.open("products.json").bufferedReader().use { it.readText() }
            val arr = JSONArray(json)
            buildList {
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val variants = buildList {
                        val va = o.optJSONArray("variants")
                        if (va != null) {
                            for (j in 0 until va.length()) {
                                val v = va.getJSONObject(j)
                                add(
                                    Variant(
                                        size = v.optString("size", ""),
                                        color = v.optString("color", ""),
                                        stock = v.optInt("stock", 0)
                                    )
                                )
                            }
                        }
                    }
                    add(
                        Product(
                            id = o.getInt("id"),
                            name = o.getString("name"),
                            price = o.getInt("price"),
                            category = o.getString("category"),
                            imageUrl = o.optString("imageUrl", null),
                            variants = variants,
                            description = o.optString("description", null)
                        )
                    )
                }
            }
        }.getOrElse { emptyList() }
    }

    suspend fun getById(id: Int): Product? = getAll().firstOrNull { it.id == id }
}
