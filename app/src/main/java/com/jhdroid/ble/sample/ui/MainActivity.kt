package com.jhdroid.ble.sample.ui

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhdroid.ble.sample.databinding.ActivityMainBinding
import com.jhdroid.ble.sample.ui.scan.DeviceScanActivity
import com.jhdroid.ble.sample.util.Constant
import com.jhdroid.ble.sample.util.getBluetoothAdapter
import com.jhdroid.ble.sample.util.missingSystemFeature
import com.jhdroid.ble.sample.util.toast

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()

        checkPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_ENABLE_BT) {
            toast(if (resultCode == Activity.RESULT_OK) "Bluetooth 활성화" else "Bluetooth 비활성화")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLowEnergyMode()
                checkBluetoothEnabled()
            } else {
                toast("권한 허용 필요 :(")
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val isDeniedPermission = (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)

            if (isDeniedPermission) {
                val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                requestPermissions(permissions, Constant.LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun setupView() {
        binding.mainDeviceScanBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, DeviceScanActivity::class.java))
        }
    }

    // 저전력 모드 지원 여부 확인
    private fun checkLowEnergyMode() {
        packageManager.takeIf { it.missingSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) }?.also {
            toast("저전력 모드를 지원하지 않음 :(")
//            finish()
        }
    }

    // 디바이스의 Bluetooth 활성화 여부 확인
    private fun checkBluetoothEnabled() {
        getBluetoothAdapter()?.takeIf { !it.isEnabled }?.apply {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, Constant.REQUEST_ENABLE_BT) // onActivityResult()로 결과 반환
        }
    }
}