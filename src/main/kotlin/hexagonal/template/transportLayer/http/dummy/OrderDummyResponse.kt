package hexagonal.template.transportLayer.http.dummy

data class OrderDummyResponse(
    val id: String,
    val dummyId: String
) {
    companion object {
        val example = OrderDummyResponse(
            id = "25069826-dcdf-46f8-95e0-0c59b93b4090",
            dummyId = "25069826-dcdf-46f8-95e0-0c59b93b4090"
        )
    }
}
