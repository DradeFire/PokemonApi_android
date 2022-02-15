package com.example.pokemons

import com.example.pokemons.api.retrofit.RetrofitInstanceApi
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.Test
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private companion object {
        const val NAME_OF_POKEMON = "pikachu"
    }

    @BeforeEach
    fun setUpRxSchedulers() {

        val immediate = object : Scheduler() {

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Runnable::run, true)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test
    fun usingRetrofitForTakePokemonByName(){
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            RetrofitInstanceApi.apiPokemon.getPokemon(NAME_OF_POKEMON)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ pokemonTest ->
                    assertNotNull(pokemonTest)
                    assertEquals(pokemonTest.name, NAME_OF_POKEMON)
                    assert(pokemonTest.abilities.isNotEmpty())
                }, {
                    assert(false)
                })
        )

    }

}