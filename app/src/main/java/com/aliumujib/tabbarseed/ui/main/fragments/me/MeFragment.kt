package com.aliumujib.tabbarseed.ui.main.fragments.me

import android.content.Context
import android.os.Bundle
import android.view.View
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.ui.base.BaseMVVMFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class MeFragment : BaseMVVMFragment<MeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }

    }


    @Inject
    override fun injectViewModel(viewModel: MeViewModel) {
        super.injectViewModel(viewModel)
    }

    override fun injectDependencies() {
        super.injectDependencies()
        AndroidSupportInjection.inject(this)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    override val layoutResID: Int
        get() = R.layout.fragment_videos_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    companion object {

        @JvmStatic
        fun newInstance() =
                MeFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
