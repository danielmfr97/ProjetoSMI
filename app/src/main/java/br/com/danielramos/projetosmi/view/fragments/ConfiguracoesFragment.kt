package br.com.danielramos.projetosmi.view.fragments

import android.R.attr
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.danielramos.projetosmi.databinding.FragmentConfiguracoesBinding
import br.com.danielramos.projetosmi.view.activities.MainActivity


class ConfiguracoesFragment : Fragment(), View.OnClickListener {
    private val TAG = "ConfiguracoesFragment"
    private var _binding: FragmentConfiguracoesBinding? = null
    private val binding get() = _binding!!

    private lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfiguracoesBinding.inflate(inflater, container, false)
        configurarBluetooth()
        configurarComponetnes()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        MainActivity.context.unregisterReceiver(broadcastReceiverOnOffBT)
        MainActivity.context.unregisterReceiver(broadcastReceiverDiscoverableBT)
    }

    fun configurarBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    private fun configurarComponetnes() {
        binding.btnOnOffBluetooth.setOnClickListener(this)
        binding.btnEnableDiscoverable.setOnClickListener(this)
    }

    private fun enableDisableBluetooth() {
        if (bluetoothAdapter == null) {
            Log.d(TAG, "Blutooth esta desligado")
        }
        if (!bluetoothAdapter.isEnabled) {
            Log.d(TAG, "Ativando bluetooth")
            //TODO: Isso deve ser perguntado ao entrar na tela
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivity(enableBluetoothIntent)
            val bluetoothFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            MainActivity.context.registerReceiver(broadcastReceiverOnOffBT, bluetoothFilter)
        } else {
            Log.d(TAG, "Desativando Bluetooth")
            bluetoothAdapter.disable()
            val bluetoothFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            MainActivity.context.registerReceiver(broadcastReceiverOnOffBT, bluetoothFilter)
        }
    }

    fun enableDeviceDiscoverable() {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.")
        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        startActivity(discoverableIntent)
        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
        MainActivity.context.registerReceiver(broadcastReceiverDiscoverableBT, intentFilter)
    }

    private val broadcastReceiverOnOffBT = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action!! == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> Log.d(TAG, "onReceive: STATE OFF")
                    BluetoothAdapter.STATE_TURNING_OFF -> Log.d(
                        TAG,
                        "mBroadcastReceiver1: STATE TURNING OFF"
                    )
                    BluetoothAdapter.STATE_ON -> Log.d(TAG, "mBroadcastReceiver1: STATE ON")
                    BluetoothAdapter.STATE_TURNING_ON -> Log.d(
                        TAG,
                        "mBroadcastReceiver1: STATE TURNING ON"
                    )
                }
            }
        }
    }
    private val broadcastReceiverDiscoverableBT = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action!! == BluetoothAdapter.ACTION_SCAN_MODE_CHANGED) {
                val mode = intent.getIntExtra(
                    BluetoothAdapter.EXTRA_SCAN_MODE,
                    BluetoothAdapter.ERROR
                )
                when (attr.mode) {
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> Log.d(
                        TAG,
                        "mBroadcastReceiver2: Discoverability Enabled."
                    )
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE -> Log.d(
                        TAG,
                        "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections."
                    )
                    BluetoothAdapter.SCAN_MODE_NONE -> Log.d(
                        TAG,
                        "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections."
                    )
                    BluetoothAdapter.STATE_CONNECTING -> Log.d(
                        TAG,
                        "mBroadcastReceiver2: Connecting...."
                    )
                    BluetoothAdapter.STATE_CONNECTED -> Log.d(
                        TAG,
                        "mBroadcastReceiver2: Connected."
                    )
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnOnOffBluetooth -> enableDisableBluetooth()
            binding.btnEnableDiscoverable -> enableDeviceDiscoverable()
        }
    }

}