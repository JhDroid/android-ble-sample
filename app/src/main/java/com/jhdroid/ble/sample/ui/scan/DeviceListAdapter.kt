package com.jhdroid.ble.sample.ui.scan

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jhdroid.ble.sample.databinding.ItemDeviceListBinding

class DeviceListAdapter: RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    private val deviceList = arrayListOf<BluetoothDevice>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDeviceListBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = deviceList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (deviceList.isNotEmpty()) {
            holder.bind(deviceList[position])
        }
    }

    fun addDevice(device: BluetoothDevice) {
        deviceList.add(device)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemDeviceListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BluetoothDevice) {
            binding.device = item
        }
    }
}