package br.eng.rodrigoamaro.treinamentotestes

import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class TipsPatternsAndLibs {

    //    Make Them Short: It's easier to understand what they do
    //    Don’t Repeat Yourself: If something appear in all tests consider moving to an Util class or method
    //    Make Them Fast: Fast tests means it can be ran several times also more time to spend programming
    //    Make Them Deterministic: It's less error and flaky prone
    //    Don’t Ignore Tests: If something is broken, fix it. If it's not necessary, delete it.
    //    Test Your Tests: Check if they fail for the right reason before trust them
    //    Name Your Tests Well: It can both give: why they exist and how the class works
    //    One Logical Assertion Per Test: Easier to maintain and understand

    @Test
    fun composition() {
        // Arrange
        val car = Car(Engine())
        car.start()

        // Act
        car.run()

        // Assert
        assertThat(Car.gas, equalTo(95))
    }

    @Test
    fun blackBox() {
        // Arrange
        val car = Car(Engine())
        car.start()

        // Act
        car.run()

        // Assert
        assertThat(Car.gas, equalTo(95))
    }

    @Test
    fun whiteBox() {
        // Arrange
        val engine = mockk<Engine>(relaxed = true)
        val car = Car(engine)
        car.start()

        // Act
        car.run()

        // Assert
        verify { engine.run() }
    }

    @Test
    fun resultPattern() {
        // Arrange
        val engine = Engine()

        // Act
        val consumed = engine.run()

        // Assert
        // Here we assert over function return
        assertThat(consumed, equalTo(5))
    }

    @Test
    fun statePattern() {
        // Arrange
        val car = Car(Engine())
        car.start()

        // Act
        car.run()

        // Assert
        // Here we assert over a value from class other than the function return
        assertThat(Car.gas, equalTo(95))
    }

    /** LAST MINUTE TIPS **/
    // What you expect from your test?
    // Get to know the most reliable tool for the job
    // Don't treat your tests like "write once and forget"
    // Found a bug? Write a test before fix it
}