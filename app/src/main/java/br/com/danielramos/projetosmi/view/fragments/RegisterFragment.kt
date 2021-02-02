package br.com.danielramos.projetosmi.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import br.com.danielramos.projetosmi.contracts.RegisterContract
import br.com.danielramos.projetosmi.databinding.FragmentRegisterBinding
import br.com.danielramos.projetosmi.presenter.RegisterPresenter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment(), RegisterContract.RegisterView {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: RegisterPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        inicializarPresenter()
        configurarBotoes()
        return view
    }

    private fun inicializarPresenter() {
        presenter = RegisterPresenter(this)
    }

    private fun configurarBotoes() {
        binding.btnEfetuarRegistro.setOnClickListener {
            registrarUsuario()
        }
    }

    override fun registrarUsuario() {
        presenter.cadastrarUsuario()
    }

    override fun getNome(): EditText {
        return binding.tieNome
    }

    override fun getEmail(): EditText {
        return binding.tieEmailRegistro
    }

    override fun getSenha(): EditText {
        return binding.tieSenha
    }

    override fun enableDisableProgressbar(flag: Boolean) {
        binding.loading.visibility = if (flag) View.VISIBLE else View.GONE
    }
}