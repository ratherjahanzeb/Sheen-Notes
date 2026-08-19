package com.example.model

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: String
)

val dummyNotes = listOf(
    Note(
        id = 1,
        title = "Winter in Kashmir",
        content = "The snow is falling gently over the Chinar trees. The entire valley is covered in a crisp, white blanket. Time seems to slow down here.",
        timestamp = "Dec 15"
    ),
    Note(
        id = 2,
        title = "Groceries",
        content = "• Kashmiri Kahwa tea leaves\n• Saffron\n• Honey\n• Almonds",
        timestamp = "Dec 16"
    ),
    Note(
        id = 3,
        title = "Design Ideas",
        content = "Minimalist, flat AppBar. Soft ice blue FAB. Frosty shadows on cards.",
        timestamp = "Dec 18"
    )
)
