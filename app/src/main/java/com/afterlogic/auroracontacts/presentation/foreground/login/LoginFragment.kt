package com.afterlogic.auroracontacts.presentation.foreground.login

import android.arch.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import com.afterlogic.auroracontacts.R
import com.afterlogic.auroracontacts.databinding.LoginFragmentBinding
import com.afterlogic.auroracontacts.presentation.common.base.MVVMFragment
import com.afterlogic.auroracontacts.presentation.common.databinding.get
import com.afterlogic.auroracontacts.presentation.common.databinding.inflateBinding

/**
 * Created by sunny on 05.12.2017.
 * mail: mail@sunnydaydev.me
 */
class LoginFragment: MVVMFragment<LoginViewModel, LoginFragmentBinding>() {

    companion object {

        fun instance(): LoginFragment = LoginFragment()

    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): LoginFragmentBinding =
            inflater.inflateBinding(R.layout.login_fragment, container)

    override fun getViewModel(provider: ViewModelProvider): LoginViewModel = provider.get()

}