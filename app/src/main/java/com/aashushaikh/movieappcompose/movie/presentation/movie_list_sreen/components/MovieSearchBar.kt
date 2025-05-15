package com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.aashushaikh.movieappcompose.utils.DarkBlue
import com.aashushaikh.movieappcompose.utils.DesertWhite
import com.aashushaikh.movieappcompose.utils.SandYellow

@Composable
fun MovieSearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onImeSearch: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow,
            backgroundColor = SandYellow.copy(alpha = 0.4f)
        )
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            maxLines = 1,
            singleLine = true,
            shape = RoundedCornerShape(100),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SandYellow,
                cursorColor = DarkBlue
            ),
            placeholder = {
                Text(
                    text = "Search Movies"
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                )
            },
            keyboardActions = KeyboardActions(
                onSearch = { onImeSearch() }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = searchQuery.isNotBlank()
                ) {
                    IconButton(
                        onClick =   {
                            onSearchQueryChanged("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                        )
                    }
                }
            },
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(100),
                    color = DesertWhite
                )
                .minimumInteractiveComponentSize()
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun MovieSearchBarPreview() {
    MovieSearchBar(
        searchQuery = "",
        onSearchQueryChanged = {},
        onImeSearch = {},
        modifier = Modifier.fillMaxWidth()
    )
}