package br.eng.rodrigoamaro.treinamentotestes

import org.junit.*

/**
 * Tests and configuration
 */
class Basics {

    private val car = Car(Engine())

    @Before
    fun setUp() {
        println("Before")
        car.start()
    }

    @After
    fun tearDown() {
        println("After")

    }

    @Test
    fun firstTest() {
        println("First Test")
        car.run()
        println("Gas: ${Car.gas}")
    }

    @Test
    fun `Second Test`() {
        println("Second Test")
        car.run()
        println("Gas: ${Car.gas}")
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun preClass() {
            println("Before Class")
        }

        @AfterClass
        @JvmStatic
        fun postClass() {
            println("After Class")
        }
    }
}


class MoreBasics {
    private val car = Car(Engine())

    @Before
    fun setUp() {
        println("Before (Again)")
    }

    @After
    fun tearDown() {
        println("After (Again)")
    }

    @Test
    fun firstTest() {
        println("First Test (Again)")
        car.run()
        println("Gas: ${Car.gas}")
    }

    @Test
    fun `Second Test`() {
        println("Second Test (Again)")
        car.run()
        println("Gas: ${Car.gas}")
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun preClass() {
            println("Before Class (Again)")
        }

        @AfterClass
        @JvmStatic
        fun postClass() {
            println("After Class (Again)")
        }
    }
}

