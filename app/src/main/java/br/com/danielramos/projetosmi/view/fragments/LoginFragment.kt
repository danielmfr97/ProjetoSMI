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
import br.com.danielramos.projetosmi.presenter.LoginPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(), View.OnClickListener, LoginContract.LoginView {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: LoginPresenter
    private lateinit var auth: FirebaseAuth
    private val TAG = "LoginFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        inicializarPresenter()
        inicializarFirebaseAuth()
        configurarBotoes()
        return view
    }

    private fun inicializarPresenter() {
        presenter = LoginPresenter(this)
    }

    private fun inicializarFirebaseAuth() {
        auth = Firebase.auth
    }

    private fun configurarBotoes() {
        binding.btnAcessar.setOnClickListener(this)
        binding.btnRegistrar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnAcessar -> {
                if (presenter.isCredentialsValid())
                    presenter.logarUsuario()
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

