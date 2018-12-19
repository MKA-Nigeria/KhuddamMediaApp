package com.aliumujib.tabbarseed.ui.main.fragments.repodetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.aliumujib.tabbarseed.R
import com.aliumujib.tabbarseed.data.model.RepositoryEntity
import com.aliumujib.tabbarseed.ui.base.BaseMVVMFragment
import com.aliumujib.tabbarseed.ui.main.IMainFragmentNavigation


class RepositoryDetailsFragment : BaseMVVMFragment<RepoDetailViewModel>() {


    lateinit var viewModelFactory: RepoDetailsViewModelModelFactory
    lateinit var fragmentNavigation: IMainFragmentNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }

        viewModelFactory = RepoDetailsViewModelModelFactory(fragmentNavigation)
        injectViewModel(ViewModelProviders.of(this, viewModelFactory).get(RepoDetailViewModel::class.java))

        val data = arguments?.getParcelable<RepositoryEntity>(REPO_DETAILS)

        getViewModel().setRepositoryData(data)

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainFragmentNavigation) {
            fragmentNavigation = context
        }
    }


    override val layoutResID: Int
        get() = R.layout.fragment_repo_details


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    companion object {
        var REPO_DETAILS = "REPO_DETAILS"

        @JvmStatic
        fun newInstance(repositoryEntity: RepositoryEntity) =
                RepositoryDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(REPO_DETAILS, repositoryEntity)
                    }
                }
    }
}
