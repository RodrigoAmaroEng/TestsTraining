package br.eng.rodrigoamaro.treinamentotestes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FragmentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_fragments)
        supportFragmentManager.beginTransaction().run {
            add(R.id.container, FragmentA())
            commit()
        }
    }

}