package br.eng.rodrigoamaro.treinamentotestes

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ElementsActivity : AppCompatActivity() {

    private lateinit var buttonBoring: Button
    private lateinit var textCounter: TextView

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elements)
        textCounter = findViewById(R.id.textCounter)
        buttonBoring = findViewById(R.id.buttonBoring)
        buttonBoring.setOnClickListener {
            textCounter.text = String.format("You pressed %d times", ++counter)
        }
        Toast.makeText(this,"Hello!", Toast.LENGTH_LONG).show()
    }
}