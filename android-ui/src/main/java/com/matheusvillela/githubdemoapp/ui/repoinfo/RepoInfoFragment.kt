package com.matheusvillela.githubdemoapp.ui.repoinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.matheusvillela.githubdemoapp.R
import com.matheusvillela.githubdemoapp.databinding.FragmentRepoInfoBinding
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoState
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoViewModel
import com.matheusvillela.githubdemoapp.shared.addTo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import toothpick.InjectConstructor

@InjectConstructor
class RepoInfoFragment(
    private val viewModel: RepoInfoViewModel
) : Fragment(R.layout.fragment_repo_info) {

    private var disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepoInfoBinding.bind(view)
        viewModel.state.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.repoName.text = it.repo.name
                binding.repoDescription.text = it.repo.description
                binding.loading.visibility = View.INVISIBLE
                when (it) {
                    is RepoInfoState.Success -> {
                        binding.userName.text = it.user.name
                        Glide
                            .with(this)
                            .load(it.user.avatarUrl)
                            .centerCrop()
                            .into(binding.image)
                    }
                    is RepoInfoState.Error -> { // TODO
                    }
                    is RepoInfoState.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                }
            }.addTo(disposables)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.dispose()
        disposables = CompositeDisposable()
    }
}