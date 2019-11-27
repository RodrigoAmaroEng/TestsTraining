package br.eng.rodrigoamaro.treinamentotestes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentA : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_goto_b).setOnClickListener { goToB() }
    }

    private fun goToB() {
        Toast.makeText(context, "Going", Toast.LENGTH_SHORT).show()
        activity?.supportFragmentManager?.beginTransaction()?.run {
            Toast.makeText(context, "Gone", Toast.LENGTH_SHORT).show()
            replace(R.id.container, FragmentB())
            commit()
        }
    }
}