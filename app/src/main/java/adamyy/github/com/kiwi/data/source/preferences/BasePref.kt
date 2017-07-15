package adamyy.github.com.kiwi.data.source.preferences

interface BasePref {

    companion object {
        const val NAME = "kiwi_preferences"
    }

    fun contains(key: String): Boolean

}