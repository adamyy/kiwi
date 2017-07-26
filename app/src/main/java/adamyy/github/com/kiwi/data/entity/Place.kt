package adamyy.github.com.kiwi.data.entity

data class Coordinate(
        val coordinates: Array<Float>,
        val type: String
)

data class Place(
        val id: String,
        val name: String,
        val placeType: String,
        // url representing the location of additional place metadata
        val url: String,
        val country: String,
        val countryCode: String,
        val fullName: String,
        val attributes: Attributes
){
    data class Attributes(
            val streetAddress: String?,
            val phone: String?,
            val postalCode: String?,
            // official/canonical url for place
            val url: String?
    )
}