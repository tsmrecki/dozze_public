package com.dozze.di

import com.dozze.ui.add_timer.AddTimerViewModel
import com.dozze.ui.timers.TimersListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelsModule = module {
    viewModelOf(::TimersListViewModel)
    viewModelOf(::AddTimerViewModel)
}