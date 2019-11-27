package br.eng.rodrigoamaro.treinamentotestes

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.squareup.rx2.idler.Rx2Idler
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.junit.Before
import org.junit.Test


class ApiCalls {

    @Before
    fun setUp() {
        RxJavaPlugins.setComputationSchedulerHandler { it }
    }

    @Test
    fun longRunningOperation() {
        ActivityScenario.launch(ApiActivity::class.java)
        onView(withId(R.id.buttonCall)).perform(click())
        onView(withText("Success")).check(matches(isDisplayed()))
    }

    @Test
    fun longRunningOperationSolution1_Sleep() {
        ActivityScenario.launch(ApiActivity::class.java)
        onView(withId(R.id.buttonCall)).perform(click())
        Thread.sleep(1500) // AVOID THIS AT ALL COST!!!
        onView(withText("Success")).check(matches(isDisplayed()))
    }

    @Test
    fun longRunningOperationSolution2_WaitUntil() {
        ActivityScenario.launch(ApiActivity::class.java)
        onView(withId(R.id.buttonCall)).perform(click())
        waitUntil({
            // STILL NOT THE BEST SOLUTION
            onView(withText("Success")).check(matches(isDisplayed()))
        })
    }

    @Test
    fun longRunningOperationSolution3_Mock() {
        ActivityScenario.launch(ApiActivity::class.java).onActivity {
            val api = mockk<Api>()
            every { api.doCall() } returns Completable.complete()
            val networkModule = mockk<NetworkModule>()
            every { networkModule.api } returns api
            it.injector = DI.Impl(networkModule)
        }
        onView(withId(R.id.buttonCall)).perform(click())
        onView(withText("Success")).check(matches(isDisplayed()))
    }

    @Test
    fun longRunningOperationSolution4_CustomIdlingResource() {
        ActivityScenario.launch(ApiActivity::class.java).onActivity {
            val resource = MyInterceptorIdlingResource()
            IdlingRegistry.getInstance().register(resource)
            it.injector = DI.Impl(MyNetworkModule(resource))
        }
        onView(withId(R.id.buttonCall)).perform(click())
        onView(withText("Success")).check(matches(isDisplayed()))
    }

    @Test
    fun longRunningOperationSolution5_RxIdler() {
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("MyScheduler"))
        ActivityScenario.launch(ApiActivity::class.java)
        onView(withId(R.id.buttonCall)).perform(click())
        onView(withText("Success")).check(matches(isDisplayed()))
    }


    private fun waitUntil(block: (() -> Unit), timeout: Int = 1000) {
        val start = System.currentTimeMillis()
        do {
            try {
                block()
                return
            } catch (ex: Exception) {

            }
        } while ((System.currentTimeMillis() - start) < timeout)
        block()
    }
}

class MyNetworkModule(private val resource: MyInterceptorIdlingResource) : NetworkModule.Impl() {
    override val client: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(resource).build()
    }
}


class MyInterceptorIdlingResource : Interceptor, IdlingResource {
    private var isIdle = true
    private var callback: IdlingResource.ResourceCallback? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        isIdle = false
        val response = chain.proceed(chain.request())
        isIdle = true
        callback?.onTransitionToIdle()
        return response
    }

    override fun getName(): String = "Api Call Idling"

    override fun isIdleNow(): Boolean = isIdle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}