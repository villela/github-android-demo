package com.matheusvillela.githubdemoapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListState
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListViewModel
import com.matheusvillela.githubdemoapp.ui.repoinfo.RepoInfoNavigator
import com.matheusvillela.githubdemoapp.ui.repolist.OnRepoClickPublisher
import com.matheusvillela.githubdemoapp.ui.repolist.OnRepoClickSubscriber
import com.matheusvillela.githubdemoapp.ui.repolist.RepoListAdapter
import com.matheusvillela.githubdemoapp.ui.repolist.RepoListFragment
import io.mockk.*
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListFragmentTest {

    private lateinit var fragmentFactory: FragmentFactory
    private lateinit var firstRepo: GitHubRepo
    private lateinit var secondRepo: GitHubRepo
    private lateinit var navigator: RepoInfoNavigator

    @Before
    fun setup() {
        navigator = mockk()
        every { navigator.navigate(*any()) } just Runs
        firstRepo = GitHubRepo(
            1,
            "repoName1",
            "repoDescription1",
            GitHubRepo.RepositoryOwner("repoUserLogin1")
        )
        secondRepo = GitHubRepo(
            2,
            "repoName2",
            "repoDescription2",
            GitHubRepo.RepositoryOwner("repoUserLogin2")
        )
        fragmentFactory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                val onClickSubscriber = OnRepoClickSubscriber()
                return RepoListFragment(
                    object : RepoListViewModel {
                        override val state = Observable.create<RepoListState> {
                            it.onNext(RepoListState.Success(listOf(firstRepo, secondRepo), ""))
                        }

                        override fun searchRepos(searchStr: String) {
                        }
                    },
                    onClickSubscriber,
                    RepoListAdapter(OnRepoClickPublisher(onClickSubscriber)),
                    navigator
                )
            }
        }
        launchFragmentInContainer<RepoListFragment>(
            factory = fragmentFactory,
            themeResId = R.style.AppTheme
        )
    }

    @Test
    fun test_shows_first_repo_name() {
        onView(withText(firstRepo.name)).check(matches(isDisplayed()))
    }

    @Test
    fun test_shows_first_repo_owner_login() {
        onView(withText(firstRepo.owner.login)).check(matches(isDisplayed()))
    }

    @Test
    fun test_shows_second_repo_name() {
        onView(withText(secondRepo.name)).check(matches(isDisplayed()))
    }

    @Test
    fun test_shows_second_repo_owner_login() {
        onView(withText(secondRepo.owner.login)).check(matches(isDisplayed()))
    }

    @Test
    fun click_first_repo_name_calls_navigation_correctly() {
        onView(withText(firstRepo.name)).perform(click())
        verify { navigator.navigate(firstRepo) }
    }

    @Test
    fun click_first_repo_owner_login_calls_navigation_correctly() {
        onView(withText(firstRepo.owner.login)).perform(click())
        verify { navigator.navigate(firstRepo) }
    }

    @Test
    fun click_second_repo_name_calls_navigation_correctly() {
        onView(withText(secondRepo.name)).perform(click())
        verify { navigator.navigate(secondRepo) }
    }

    @Test
    fun click_second_repo_owner_login_calls_navigation_correctly() {
        onView(withText(secondRepo.owner.login)).perform(click())
        verify { navigator.navigate(secondRepo) }
    }
}