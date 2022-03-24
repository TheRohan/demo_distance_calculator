package com.rohan.demodistancecalculator.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rohan.demodistancecalculator.R
import com.rohan.demodistancecalculator.data.db.DistanceInfo
import com.rohan.demodistancecalculator.databinding.DistanceItemElementBinding
import com.rohan.demodistancecalculator.other.Utility.formatFloatToDisplay
import com.rohan.demodistancecalculator.other.Utility.millsToText
import java.text.SimpleDateFormat
import java.util.*


class DistanceInfoAdapter(
    private val c: Context,
    private val iSelectItem: ISelectItem,
) : RecyclerView.Adapter<DistanceInfoAdapter.DistanceInfoViewHolder>() {

    val diffCallback = object : DiffUtil.ItemCallback<DistanceInfo>() {
        override fun areItemsTheSame(oldItem: DistanceInfo, newItem: DistanceInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DistanceInfo, newItem: DistanceInfo): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<DistanceInfo>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistanceInfoViewHolder {
        val bindind = DistanceItemElementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DistanceInfoViewHolder(bindind)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: DistanceInfoViewHolder, position: Int) {
        val item = differ.currentList[position]
        with(holder) {

            binding.tvStartPoint.text =
                "${c.getString(R.string.start_point)}\t\t${formatFloatToDisplay(item.locationInfo1.lat)}/${
                    formatFloatToDisplay(item.locationInfo1.lon)
                }"

            binding.tvEndPoint.text =
                "${c.getString(R.string.end_point)}\t\t${formatFloatToDisplay(item.locationInfo2.lat)}/${
                    formatFloatToDisplay(item.locationInfo2.lon)
                }"

            binding.tvDistance.text =
                "${c.getString(R.string.distance_m)}\t\t${item.distanceInM}m"

            val dateString = millsToText(item.createdDate)

            binding.tvDate.text =
                "${c.getString(R.string.date)}\t\t$dateString"

            binding.holder.setOnClickListener {
                iSelectItem.itemSelect(item.id ?: -1)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class DistanceInfoViewHolder(val binding: DistanceItemElementBinding) :
        RecyclerView.ViewHolder(binding.root)

}
