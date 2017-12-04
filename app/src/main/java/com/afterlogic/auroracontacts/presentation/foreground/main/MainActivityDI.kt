package com.afterlogic.auroracontacts.presentation.foreground.main

import android.arch.lifecycle.ViewModel
import com.afterlogic.auroracontacts.presentation.FragmentScope
import com.afterlogic.auroracontacts.presentation.common.databinding.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by sunny on 04.12.2017.
 * mail: mail@sunnydaydev.me
 */


@Module
internal abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModelToMap(vm: MainViewModel): ViewModel

    @FragmentScope
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun contributeMainFragment(): MainFragment

}

@Module
internal abstract class MainFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun bindViewModel(vm: MainFragmentViewModel): ViewModel

}