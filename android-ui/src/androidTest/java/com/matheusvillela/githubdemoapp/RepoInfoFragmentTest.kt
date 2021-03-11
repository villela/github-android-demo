package com.matheusvillela.githubdemoapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubUser
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoState
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoViewModel
import com.matheusvillela.githubdemoapp.ui.repoinfo.RepoInfoFragment
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoInfoFragmentTest {

    private lateinit var fragmentFactory: FragmentFactory

    @Before
    fun setup() {
        fragmentFactory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return RepoInfoFragment(object : RepoInfoViewModel {
                    override val state = Observable.create<RepoInfoState> {
                        it.onNext(
                            RepoInfoState.Success(
                                GitHubRepo(
                                    1,
                                    "repoName",
                                    "repoDescription",
                                    GitHubRepo.RepositoryOwner("repoUserLogin")
                                ),
                                GitHubUser(
                                    "repoUserName",
                                    "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png"
                                )
                            )
                        )
                    }

                    override fun reloadRepoInfo() {
                    }
                })
            }
        }
        launchFragmentInContainer<RepoInfoFragment>(
            factory = fragmentFactory,
            themeResId = R.style.AppTheme
        )
    }

    @Test
    fun test_owner_name_is_displayed() {
        onView(withText("repoUserName")).check(matches(isDisplayed()))
    }

    @Test
    fun test_repo_name_is_displayed() {
        onView(withText("repoName")).check(matches(isDisplayed()))
    }

    @Test
    fun test_repo_description_id_displayed() {
        onView(withText("repoDescription")).check(matches(isDisplayed()))
    }
}