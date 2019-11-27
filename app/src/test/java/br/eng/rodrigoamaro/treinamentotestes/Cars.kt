package br.eng.rodrigoamaro.treinamentotestes

class Engine {
    fun run(): Int = 5
}

class Car(private val engine: Engine) {
    companion object {
        var gas: Int = 0
    }

    fun start() {
        gas = 100
    }

    fun run() {
        gas -= engine.run()
    }
}