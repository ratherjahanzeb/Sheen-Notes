package com.example.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.FormatListBulleted
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.FormatItalic
import androidx.compose.material.icons.rounded.FormatUnderlined
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun AddNoteScreen(
    viewModel: NotesViewModel,
    noteId: Int = -1,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBackClick: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    val richTextState = rememberRichTextState()
    var isLoaded by remember { mutableStateOf(noteId == -1) }

    LaunchedEffect(noteId) {
        if (noteId != -1) {
            val note = viewModel.getNoteById(noteId)
            if (note != null) {
                title = note.title
                richTextState.setHtml(note.content)
            }
            isLoaded = true
        }
    }

    if (!isLoaded) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background))
        return
    }

    val sharedKey = if (noteId == -1) "fab" else "note-$noteId"

    with(sharedTransitionScope) {
        Scaffold(
            modifier = Modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = sharedKey),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ -> tween(durationMillis = 400) }
                )
                .imePadding(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    actions = {
                        if (noteId != -1) {
                            IconButton(onClick = {
                                viewModel.deleteNoteById(noteId)
                                onBackClick()
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        TextButton(
                            onClick = {
                                val bodyHtml = richTextState.toHtml()
                                if (title.isNotBlank() || bodyHtml.isNotBlank()) {
                                    if (noteId != -1) {
                                        viewModel.updateNote(noteId, title, bodyHtml)
                                    } else {
                                        viewModel.addNote(title, bodyHtml)
                                    }
                                }
                                onBackClick()
                            }
                        ) {
                            Text(
                                text = "Save",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        scrolledContainerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            bottomBar = {
                // Sleek Custom Formatting Toolbar
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 2.dp,
                    shadowElevation = 4.dp
                ) {
                    val currentSpanStyle = richTextState.currentSpanStyle
                    val isBold = currentSpanStyle.fontWeight == FontWeight.Bold
                    val isItalic = currentSpanStyle.fontStyle == FontStyle.Italic
                    val isUnderlined = currentSpanStyle.textDecoration == TextDecoration.Underline
                    val isUnorderedList = richTextState.isUnorderedList

                    val activeColor = MaterialTheme.colorScheme.primary
                    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = { richTextState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold)) }) {
                            Icon(Icons.Rounded.FormatBold, contentDescription = "Bold", tint = if (isBold) activeColor else inactiveColor)
                        }
                        IconButton(onClick = { richTextState.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic)) }) {
                            Icon(Icons.Rounded.FormatItalic, contentDescription = "Italic", tint = if (isItalic) activeColor else inactiveColor)
                        }
                        IconButton(onClick = { richTextState.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline)) }) {
                            Icon(Icons.Rounded.FormatUnderlined, contentDescription = "Underline", tint = if (isUnderlined) activeColor else inactiveColor)
                        }
                        IconButton(onClick = { richTextState.toggleUnorderedList() }) {
                            Icon(Icons.AutoMirrored.Rounded.FormatListBulleted, contentDescription = "Bullet List", tint = if (isUnorderedList) activeColor else inactiveColor)
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(
                            text = "Title",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                RichTextEditor(
                    state = richTextState,
                    placeholder = {
                        Text(
                            text = "Type your note here...",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 26.sp
                    ),
                    colors = RichTextEditorDefaults.richTextEditorColors(
                        containerColor = MaterialTheme.colorScheme.background,
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
}
