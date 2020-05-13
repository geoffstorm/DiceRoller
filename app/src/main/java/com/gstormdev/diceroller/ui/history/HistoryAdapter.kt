package com.gstormdev.diceroller.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gstormdev.diceroller.R
import com.gstormdev.diceroller.databinding.RowRollHistoryBinding
import com.gstormdev.diceroller.db.entity.RollHistory
import com.gstormdev.diceroller.util.Formatters

class HistoryAdapter(private val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<HistoryViewHolder>() {
    private var rolls: List<RollHistoryItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowRollHistoryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_roll_history, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return rolls.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(rolls[position])
    }

    fun setData(history: List<RollHistory>) {
        val rolls = history.map { RollHistoryItem(it) }
        val diffResult = DiffUtil.calculateDiff(RollHistoryDiffCallback(rolls, this.rolls))
        diffResult.dispatchUpdatesTo(this)
        this.rolls = rolls
    }
}

class HistoryViewHolder(private val binding: RowRollHistoryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: RollHistoryItem) {
        binding.model = model
        binding.executePendingBindings()
    }
}

class RollHistoryDiffCallback(private val newData: List<RollHistoryItem>, private val data: List<RollHistoryItem>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return data.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = data[oldItemPosition]
        val newItem = newData[newItemPosition]
        return oldItem.rollHistory.id == newItem.rollHistory.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = data[oldItemPosition]
        val newItem = newData[newItemPosition]
        return oldItem.rollHistory == newItem.rollHistory
    }
}

class RollHistoryItem(val rollHistory: RollHistory) {
    private val _isExpanded = MutableLiveData(false)
    val isExpanded: LiveData<Boolean> = _isExpanded
    val expandCollapseDrawable: LiveData<Int> = Transformations.map(isExpanded) { expanded ->
        if (expanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more
    }

    fun getRollTotal(): String {
        return rollHistory.rollResult.result.toString()
    }

    fun getDiceNotation(): String {
        val params = rollHistory.rollParameters
        return Formatters.diceNotationFormatter(params.dieCount, params.sideCount, params.constant)
    }

    fun getDate(): Long {
        return rollHistory.rollDateTime
    }

    fun getRolls(): List<Int> {
        return rollHistory.rollResult.rolls
    }

    fun toggleExpanded() {
        _isExpanded.value = !(_isExpanded.value ?: false)
    }
}