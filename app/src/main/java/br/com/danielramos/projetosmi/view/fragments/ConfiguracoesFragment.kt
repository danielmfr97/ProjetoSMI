package br.com.danielramos.projetosmi.view.fragments

import android.R.attr
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import br.com.danielramos.projetosmi.MyApplication
import br.com.danielramos.projetosmi.databinding.FragmentConfiguracoesBinding
import br.com.danielramos.projetosmi.presenter.ConfiguracoesPresenter
import br.com.danielramos.projetosmi.presenter.services.BluetoothConnectionService
import br.com.danielramos.projetosmi.view.activities.MainActivity
import java.nio.charset.Charset
import java.util.*


class ConfiguracoesFragment : Fragment(), View.OnClickListener, AdapterView.OnItemClickListener {
    private val TAG = "ConfiguracoesFragment"
    private var _binding: FragmentConfiguracoesBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: ConfiguracoesPresenter
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var mDeviceList = arrayListOf<BluetoothDevice>()

    lateinit var mBluetoothConnection: BluetoothConnectionService
    lateinit var mBTDevice: BluetoothDevice
    private val MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfiguracoesBinding.inflate(inflater, container, false)
        inicializarPresenter()
        configurarBluetooth()
        configurarBondBT()
        configurarComponetnes()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filter = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        MainActivity.context.registerReceiver(broadcastBondStateBT, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        MainActivity.context.unregisterReceiver(broadcastReceiverOnOffBT)
        MainActivity.context.unregisterReceiver(broadcastReceiverDiscoverableBT)
        MainActivity.context.unregisterReceiver(broadcastDiscoverBTDevices)
        MainActivity.context.unregisterReceiver(broadcastBondStateBT)
    }

    private fun inicializarPresenter() {
        presenter = ConfiguracoesPresenter(this)
    }

    fun configurarBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    private fun configurarComponetnes() {
        binding.btnOnOffBluetooth.setOnClickListener(this)
        binding.btnEnableDiscoverable.setOnClickListener(this)
        binding.btnDiscoverDevices.setOnClickListener(this)
        binding.btnStartConnection.setOnClickListener(this)
        binding.btnEnviarDados.setOnClickListener(this)
        binding.listDevices.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            bluetoothAdapter.cancelDiscovery()
            mDeviceList[i].createBond()
            mBTDevice = mDeviceList[i];
            mBluetoothConnection = BluetoothConnectionService(MainActivity.context)
        }
    }

    private fun startConnection() {
        startBTConnection(mBTDevice, MY_UUID_INSECURE)
    }

    private fun startBTConnection(device: BluetoothDevice, uuid: UUID) {
        Log.d(TAG, "Start bluetooth connection ")
        mBluetoothConnection.startClient(device, uuid)
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

    private fun enableDeviceDiscoverable() {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.")
        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        startActivity(discoverableIntent)
        val discoverableFilter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
        MainActivity.context.registerReceiver(broadcastReceiverDiscoverableBT, discoverableFilter)
    }

    private fun discoverDevices() {
        Log.d(TAG, "Descobrir")
        mDeviceList = arrayListOf()
        bluetoothAdapter.startDiscovery()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        MainActivity.context.registerReceiver(broadcastDiscoverBTDevices, filter)
    }

    private fun enviarDados() {
        val bytes: ByteArray = binding.tieTelefone.text.toString().toByteArray(Charset.defaultCharset())
        mBluetoothConnection.write(bytes)
        Log.d(TAG, "Dados enviados $bytes ")
    }

    private fun configurarBondBT() {
        binding.listDevices.onItemClickListener = this
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
    private val broadcastDiscoverBTDevices = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device = intent
                    .getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                if (device != null) {
                    mDeviceList.add(device)
                }
                binding.listDevices.adapter = ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1, mDeviceList
                )
            }
        }
    }
    private val broadcastBondStateBT = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED == action) {
                val device = intent
                    .getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                when (device!!.bondState) {
                    BluetoothDevice.BOND_BONDED -> {
                        Log.d(TAG, "BroadcastReceiver: BOND_BONDED")
                        mBTDevice = device
                    }
                    BluetoothDevice.BOND_BONDING -> {
                        Log.d(TAG, "BroadcastReceiver: BOND_BONDING")
                    }
                    BluetoothDevice.BOND_NONE -> {
                        Log.d(TAG, "BroadcastReceiver: BOND_DONE")
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnOnOffBluetooth -> enableDisableBluetooth()
            binding.btnEnableDiscoverable -> enableDeviceDiscoverable()
            binding.btnDiscoverDevices -> discoverDevices()
            binding.btnStartConnection -> startConnection()
            binding.btnEnviarDados -> enviarDados()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG, "onItemCLicked: U clicked a device")
        val deviceName = mDeviceList[position].name
        val deviceAddress = mDeviceList[position].address
        Log.d(TAG, "onItemCLicked: $deviceName : $deviceAddress")
        mDeviceList[position].createBond()

    }
}