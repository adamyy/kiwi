package adamyy.github.com.kiwi.data

import com.google.gson.Gson
import java.io.Serializable

inline fun <reified T> Gson.fromJson(json: String): T {
    return Gson().fromJson(json, T::class.java)
}

inline fun <reified T> T.toJson(): String {
    return Gson().toJson(this)
}