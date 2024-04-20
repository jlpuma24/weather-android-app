package co.bold.weather.views

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import co.bold.weather.R
import org.hamcrest.CoreMatchers.not
import org.junit.Test

class MainSearchActivityTest {
    @Test
    fun testSearchFunctionality() {
        val scenario = ActivityScenario.launch(MainSearchActivity::class.java)
        onView(withId(R.id.autoCompleteEditText)).perform(typeText("New York"), pressImeActionButton())
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.rvResults)).check(matches(isDisplayed()))
        scenario.close()
    }
}