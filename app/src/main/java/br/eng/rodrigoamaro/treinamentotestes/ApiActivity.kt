package br.eng.rodrigoamaro.treinamentotestes

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


class ApiActivity : AppCompatActivity() {

    private val button: Button by lazy { findViewById<Button>(R.id.buttonCall) }
    private val textStatus: TextView by lazy { findViewById<TextView>(R.id.textStatus) }
    var injector = Injector.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        button.setOnClickListener { load() }
    }

    private fun load() {
        injector.api.doCall()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { it.printStackTrace() }
            .subscribe({ textStatus.text = "Success" }, { textStatus.text = "Failure" })
    }
}

interface NetworkModule {
    val api: Api
    val retrofit: Retrofit
    val client: OkHttpClient

    open class Impl : NetworkModule {
        override val client: OkHttpClient by lazy { OkHttpClient() }
        override val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }
        override val api: Api by lazy { retrofit.create(Api::class.java) }
    }
}

interface DI : NetworkModule {
    class Impl(networkModule: NetworkModule) : DI, NetworkModule by networkModule
}

object Injector {
    fun create(): DI {
        return DI.Impl(NetworkModule.Impl())
    }
}

interface Api {
    @GET("/")
    fun doCall(): Completable
}