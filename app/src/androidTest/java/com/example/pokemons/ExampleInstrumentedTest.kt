package com.example.pokemons

import android.content.Intent
import android.view.Gravity
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions.*
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.rule.ActivityTestRule
import com.example.pokemons.api.room.PokemonEntity
import com.example.pokemons.api.room.PokemonsDao
import com.example.pokemons.api.room.PokemonsDatabase
import com.example.pokemons.fragments.adapters.favorites.AdapterFavorites
import com.example.pokemons.json.optionals.Ability
import com.example.pokemons.json.optionals.AbilityX
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private companion object {
        const val NAME_OF_POKEMON = "pikachu"
        val TEST_POKEMON = PokemonEntity(
            0,
            "name",
            1,
            1,
            1,
            1,
            1,
            1,
            listOf(Ability(AbilityX("skill","123"), false, 1))
        )
    }

    @Nested
    inner class TestingFragments{

        private lateinit var activityScenario: ActivityScenario<MainActivity>

        @BeforeEach
        fun setupActivityScenario(){
            activityScenario = launchActivity()
            activityScenario.moveToState(Lifecycle.State.RESUMED)
        }

        @Test
        @DisplayName("Start fragment view")
        fun testingStartFragment(){

            onView(withId(R.id.fragment_start))
                .check(matches(isDisplayed()))

            Thread.sleep(4000)

            onView(withId(R.id.fragment_start))
                .check(matches(isDisplayed()))

            activityScenario.close()

        }

        @Test
        @DisplayName("Navigate by DrawerLayout")
        fun testingDrawerLayoutNavigation(){

            Thread.sleep(7500)

            onView(withId(R.id.drawerLayout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
            onView(withId(R.id.nav_to_find_pokemon))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())
            onView(withId(R.id.fragment_find_pokemon_by_name))
                .check(matches(isCompletelyDisplayed()))

            onView(withId(R.id.drawerLayout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
            onView(withId(R.id.nav_to_random_pokemon))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())
            onView(withId(R.id.fragment_random_pokemons))
                .check(matches(isCompletelyDisplayed()))

        }

        @Test
        @DisplayName("Find pokemon by his(her) name")
        fun testingFindPokemonByName(){

            Thread.sleep(7500)

            onView(withId(R.id.btFromMainMenuToFindPokemonByName))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())

            onView(withId(R.id.fragment_find_pokemon_by_name))
                .check(matches(isDisplayed()))

            onView(withId(R.id.inputFindPokemonByName))
                .perform(typeText(NAME_OF_POKEMON))
                .check(matches(withSubstring(NAME_OF_POKEMON)))
                .perform(closeSoftKeyboard())

            onView(withId(R.id.btFindPokemonByName))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())

            Thread.sleep(5_000)

            onView(withId(R.id.CL_PokemonItem_Find))
                .check(matches(isDisplayed()))
            onView(withId(R.id.tvPokemonName))
                .check(matches(withSubstring("")))

        }

        @Test
        @DisplayName("Random pokemon")
        fun testingRandomPokemon(){

            Thread.sleep(7500)

            onView(withId(R.id.btFromMainMenuToRandom))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())
            onView(withId(R.id.fragment_random_pokemons))
                .check(matches(isDisplayed()))

            onView(withId(R.id.btRandomPokemon))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())

            Thread.sleep(5_000)

            onView(withId(R.id.CL_PokemonItem_Random))
                .check(matches(isDisplayed()))
            onView(withId(R.id.tvPokemonName))
                .check(matches(withSubstring("")))

        }

        @Test
        @DisplayName("Adding to favorites list(with UI)")
        fun testingAddingToFavoritesList(){

            Thread.sleep(7500)

            onView(withId(R.id.btFromMainMenuToFindPokemonByName))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())
            onView(withId(R.id.fragment_find_pokemon_by_name))
                .check(matches(isDisplayed()))

            onView(withId(R.id.inputFindPokemonByName))
                .perform(typeText(NAME_OF_POKEMON))
                .check(matches(withSubstring(NAME_OF_POKEMON)))
                .perform(closeSoftKeyboard())
            onView(withId(R.id.btFindPokemonByName))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())

            Thread.sleep(5_000)

            onView(withId(R.id.tvPokemonName))
                .check(matches(isDisplayed()))
            onView(withId(R.id.btAddToFavorites))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())

            onView(withId(R.id.btOpenFavoritesFragment))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click())

            Thread.sleep(5000)

            onView(withId(R.id.fragment_list_of_favorites_pokemons))
                .check(matches(isDisplayed()))
            onView(withId(R.id.rcViewFavorites))
                .perform(RecyclerViewActions.scrollTo<AdapterFavorites.ItemViewHolder>(
                    hasDescendant(withSubstring(NAME_OF_POKEMON))
                ))
                .check(matches(hasDescendant(withSubstring(NAME_OF_POKEMON))))


        }

    }

    @Nested
    inner class TestingDB{

        @get:Rule
        val mActivityRule: ActivityTestRule<MainActivity> =
            ActivityTestRule(MainActivity::class.java)
        private lateinit var activity: MainActivity
        private lateinit var userDao: PokemonsDao
        private lateinit var db: PokemonsDatabase

        @BeforeEach
        fun createDb() {
            mActivityRule.launchActivity(Intent())
            activity = mActivityRule.activity
            val context = activity.applicationContext

            db = Room.inMemoryDatabaseBuilder(
                context, PokemonsDatabase::class.java).build()
            userDao = db.pokemonDao()
        }

        @AfterEach
        fun closeDb() {
            db.close()
        }

        @Test
        @DisplayName("Adding to favorites list(functional)")
        fun addPokemonAndReadInList() {
            val pokemon = TEST_POKEMON

            runBlocking(Dispatchers.IO) {
                userDao.deleteAll()

                userDao.addPokemon(pokemon)
                delay(1000)

                val compositeDisposable = CompositeDisposable().add(
                    userDao.readAllData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ listAllPokemons ->
                        assertNotNull(listAllPokemons)
                        assertEquals(listAllPokemons[0].hp, 1)
                        assertEquals(listAllPokemons[0].name, "name")
                    },{})
                )

            }

        }

    }

}