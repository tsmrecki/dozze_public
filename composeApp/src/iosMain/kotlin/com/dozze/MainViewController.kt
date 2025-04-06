package com.dozze

import androidx.compose.ui.window.ComposeUIViewController
import com.dozze.di.initKoin
import com.dozze.requirements.LibrariesDependencyFactory

fun MainViewController(libraryFactory: LibrariesDependencyFactory) =
    ComposeUIViewController(configure = { initKoin(libraryFactory = libraryFactory) }) {
        App()
    }
