package com.jhdroid.ble.sample.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast

fun PackageManager.missingSystemFeature(name: String) = !hasSystemFeature(name)

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) = Toast.makeText(this, message, duration).show()

fun Context.getBluetoothAdapter(): BluetoothAdapter? {
    val bluetoothManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    return bluetoothManager.adapter
}