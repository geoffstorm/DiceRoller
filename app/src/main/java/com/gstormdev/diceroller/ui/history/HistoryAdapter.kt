package com.gstormdev.diceroller.ui.history

import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gstormdev.diceroller.R
import com.gstormdev.diceroller.databinding.RowRollHistoryBinding
import com.gstormdev.diceroller.db.entity.RollHistory
import com.gstormdev.diceroller.util.AnimationUtil
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
        setupViews()
    }

    private fun setupViews() {
        binding.ivCollapse.setOnClickListener {
            binding.model?.toggleExpanded()
            val currentlyExpanded = binding.model?.isExpanded ?: false

            val rotationAnimator = AnimationUtil.rotate180(binding.ivCollapse, currentlyExpanded)
            val expansionAnimator = if (currentlyExpanded) {
                AnimationUtil.expand(binding.layoutRow, binding.expandableContent)
            } else {
                AnimationUtil.collapse(binding.expandableContent)
            }

            AnimatorSet().apply {
                duration = if (currentlyExpanded) AnimationUtil.ENTRANCE_DURATION else AnimationUtil.EXIT_DURATION
                playTogether(expansionAnimator, rotationAnimator)
            }.start()
        }

        val currentlyExpanded = binding.model?.isExpanded ?: false
        binding.ivCollapse.rotation = if (currentlyExpanded) 180f else 0f
        binding.expandableContent.isVisible = currentlyExpanded
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
    var isExpanded = false

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
       isExpanded = !isExpanded
    }
}