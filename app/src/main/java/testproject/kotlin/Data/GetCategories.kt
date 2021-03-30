package testproject.kotlin.Data

data class GetCategories(
	val categories: List<CategoriesItem>,
	val status: String
)

data class CategoriesItem(
	val name: String,
	val id: String
)

