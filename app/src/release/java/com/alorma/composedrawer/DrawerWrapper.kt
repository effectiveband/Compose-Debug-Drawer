package com.alorma.composedrawer

import androidx.compose.runtime.Composable

@Composable
fun ConfigureScreen(bodyContent: @Composable (isDrawerOpen: Boolean) -> Unit) {
    bodyContent(false)
}