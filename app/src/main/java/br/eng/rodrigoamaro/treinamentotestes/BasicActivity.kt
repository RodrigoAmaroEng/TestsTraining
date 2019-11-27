package br.eng.rodrigoamaro.treinamentotestes

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class BasicActivity : AppCompatActivity() {

    private lateinit var buttonTouch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        println("TEST_STEP: Activity onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        if (intent.hasExtra("SOME_PARAM")) {
            println("TEST_STEP: Param is ${intent.getStringExtra("SOME_PARAM")}")
        }
        buttonTouch = findViewById(R.id.buttonTouch)
        buttonTouch.setOnClickListener { AlertDialog.Builder(this).setMessage("Hey i'm here!").show() }
    }
}
