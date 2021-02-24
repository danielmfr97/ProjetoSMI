package br.com.danielramos.projetosmi.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.danielramos.projetosmi.R
import br.com.danielramos.projetosmi.databinding.FragmentDashboardBinding
import br.com.danielramos.projetosmi.view.activities.MainActivity

class DashboardFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        initViews()
        return view
    }

    fun initViews() {
        binding.dashReportsID.setOnClickListener(this)
        binding.dashLocalizationID.setOnClickListener(this)
        binding.dashCallID.setOnClickListener(this)
        binding.dashAlarmID.setOnClickListener(this)
        binding.dashSetupID.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v) {
            binding.dashSetupID -> findNavController().navigate(R.id.action_open_configuracao_fragment)
        }
    }
}