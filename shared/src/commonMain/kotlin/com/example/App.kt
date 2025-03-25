package com.example

import VersionConfig
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun App() {
    Text("Current app version is ${VersionConfig.VERSION_NAME}")
}
