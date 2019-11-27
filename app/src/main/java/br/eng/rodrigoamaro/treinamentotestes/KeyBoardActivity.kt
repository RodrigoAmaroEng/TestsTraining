package br.eng.rodrigoamaro.treinamentotestes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class KeyBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyboard)
        findViewById<Button>(R.id.button_hi).setOnClickListener {
            val name = findViewById<EditText>(R.id.field_name).text ?: "Nobody"
            findViewById<TextView>(R.id.text_greetings).text = "HI $name!"
        }
    }
}