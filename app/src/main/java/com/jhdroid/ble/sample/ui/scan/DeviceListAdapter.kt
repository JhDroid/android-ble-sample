package com.jhdroid.ble.sample.ui.scan

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhdroid.ble.sample.R
import com.jhdroid.ble.sample.databinding.ItemDeviceListBinding

class DeviceListAdapter: RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    private val deviceList = arrayListOf<BluetoothDevice>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_device_list, parent, false)
    )

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

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var binding = ItemDeviceListBinding.bind(itemView)

        fun bind(item: BluetoothDevice) {
            binding.device = item
        }
    }
}