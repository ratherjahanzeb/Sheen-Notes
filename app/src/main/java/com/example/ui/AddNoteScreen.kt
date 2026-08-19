package com.example.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    viewModel: NotesViewModel,
    onBackClick: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    val charcoal = Color(0xFF333333)
    val iceBlue = Color(0xFF3B82F6)
    val snowWhite = Color.White

    Scaffold(
        containerColor = snowWhite,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = charcoal
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (title.isNotBlank() || body.isNotBlank()) {
                                viewModel.addNote(title, body)
                            }
                            onBackClick()
                        }
                    ) {
                        Text(
                            text = "Save",
                            color = iceBlue,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = snowWhite,
                    scrolledContainerColor = snowWhite
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(snowWhite)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text(
                        text = "Title",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = charcoal.copy(alpha = 0.4f)
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = charcoal
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = snowWhite,
                    unfocusedContainerColor = snowWhite,
                    disabledContainerColor = snowWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            TextField(
                value = body,
                onValueChange = { body = it },
                placeholder = {
                    Text(
                        text = "Type your note here...",
                        fontSize = 18.sp,
                        color = charcoal.copy(alpha = 0.4f)
                    )
                },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 18.sp,
                    color = charcoal,
                    lineHeight = 26.sp
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = snowWhite,
                    unfocusedContainerColor = snowWhite,
                    disabledContainerColor = snowWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}
