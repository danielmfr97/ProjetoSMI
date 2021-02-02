package br.com.danielramos.projetosmi.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.danielramos.projetosmi.databinding.FragmentConfiguracaoBinding

class ConfiguracaoFragment : Fragment() {
    private var _binding: FragmentConfiguracaoBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfiguracaoBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}