package com.example.tugashalamanawal

data class RecipeSection(
    val viewType: Int, // IMAGE, TITLE, DESCRIPTION, etc.
    val content: String = "" // For drawable name or text content
)

object ViewType {
    const val IMAGE = 0
    const val TITLE = 1
    const val DESCRIPTION = 2
    const val INGREDIENTS = 3
    const val INSTRUCTIONS = 4
}
