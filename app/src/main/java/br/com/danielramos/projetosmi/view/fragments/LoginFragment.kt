package br.com.danielramos.projetosmi.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.danielramos.projetosmi.R
import br.com.danielramos.projetosmi.contracts.LoginContract
import br.com.danielramos.projetosmi.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener, LoginContract.LoginView {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        configurarBotoes()
        return view
    }

    private fun configurarBotoes() {
        binding.tilEmail.setOnClickListener(this)
        binding.btnRegistrar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnAcessar -> {
                //TODO: Verificar se há conexão com a  internet
            }

            binding.btnRegistrar -> {
                findNavController().navigate(R.id.action_loginfragment_to_registerfragment)
            }
        }
    }

    override fun getEmail(): String {
        if (binding.tieEmail.text.toString().trim() { it <= ' ' }.isEmpty())
            binding.tilEmail.error = "Campo Obrigatório"
        return binding.tieEmail.text.toString().trim() { it <= ' ' }
    }

    override fun getSenha(): String {
        if (binding.tieSenha.text.toString().trim() { it <= ' ' }.isEmpty())
            binding.tilSenha.error = "Campo Obrigatório"
        return binding.tieSenha.text.toString().trim() { it <= ' ' }
    }
}

