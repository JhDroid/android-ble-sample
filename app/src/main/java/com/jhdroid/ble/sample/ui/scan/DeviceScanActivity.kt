package com.jhdroid.ble.sample.ui.scan

import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhdroid.ble.sample.databinding.ActivityDeviceScanBinding
import com.jhdroid.ble.sample.util.Constant
import com.jhdroid.ble.sample.util.getBluetoothAdapter
import com.jhdroid.ble.sample.util.toast
import timber.log.Timber

class DeviceScanActivity: AppCompatActivity() {

    private val binding by lazy { ActivityDeviceScanBinding.inflate(layoutInflater) }

    private val bluetoothAdapter by lazy { getBluetoothAdapter() }
    private val deviceListAdapter = DeviceListAdapter()

    private var isScanning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()

        scanDevice()
    }

    override fun onDestroy() {
        super.onDestroy()

        stopScan()
    }

    private fun setupRecyclerView() {
        with(binding.deviceScanListRv) {
            adapter = deviceListAdapter
            layoutManager = LinearLayoutManager(this@DeviceScanActivity)
            addItemDecoration(DividerItemDecoration(this@DeviceScanActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun scanDevice() {
        if (bluetoothAdapter?.isEnabled == true) {
            Handler(Looper.getMainLooper()).postDelayed({
                isScanning = false
                stopScan()
                setLoadingViewVisibility(false)
                if (deviceListAdapter.itemCount == 0) {
                    finish()
                }
            }, Constant.SCAN_PERIOD)

            isScanning = true
            startDeviceScan()
            setLoadingViewVisibility(true)
        } else {
            isScanning = false
            stopScan()
            setLoadingViewVisibility(false)
            toast("Bluetooth ????????? ????????? ?????? :(")
            finish()
        }
    }

    private fun startDeviceScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter?.bluetoothLeScanner?.startScan(scanCallback)
        } else {
            bluetoothAdapter?.startLeScan(leScanCallback)
        }
    }

    private fun stopScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(scanCallback)
        } else {
            bluetoothAdapter?.startLeScan(leScanCallback)
        }

        toast("?????? ?????? :)")
    }

    // device, rssi, scanRecord
    private val leScanCallback = BluetoothAdapter.LeScanCallback { device, _, _ ->
        Timber.d("device info : ${device.name} : ${device.address}")
        deviceListAdapter.addDevice(device)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)

            result?.device?.let {
                Timber.d("device info : ${it.name} : ${it.address}")
                deviceListAdapter.addDevice(it)
            }
        }
    }
    
    private fun setLoadingViewVisibility(isVisible: Boolean) {
        binding.deviceScanLoadingViewContainer.isVisible = isVisible
    }
}