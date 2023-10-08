package id.daniel.baseapplication.model

import com.google.gson.annotations.SerializedName

data class MovieReview(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: ArrayList<ResultReviews>?,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)

data class ResultReviews(
    @SerializedName("author")
    val author: String = "",
    @SerializedName("author_details")
    val authorDetails: AuthorDetails,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)

data class AuthorDetails(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("avatar_path")
    val avatarPath: String? = null
)