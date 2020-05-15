package com.gstormdev.diceroller.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.LifecycleOwner
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
        // TODO move this listener to the bind method?
        // TODO need to reset the expand/collapse state of the view (currently expanding the top view expands the bottom as well)
        holder.binding.ivCollapse.setOnClickListener {
            holder.binding.model?.toggleExpanded()
            val currentlyExpanded = holder.binding.model?.isExpanded ?: false
            rotate180(holder.binding.ivCollapse, currentlyExpanded)
            if (currentlyExpanded) {
                expand(holder.binding.layoutRow, holder.binding.expandableContent)
            } else {
                collapse(holder.binding.expandableContent)
            }
        }
    }

    // TODO try using a ValueAnimator as well, for funsies
    private fun expand(container: View, expandable: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(container.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        expandable.measure(widthSpec, heightSpec)
        val targetHeight = expandable.measuredHeight
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                expandable.layoutParams.height = if (interpolatedTime == 1f) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    (targetHeight * interpolatedTime).toInt()
                }
                expandable.requestLayout()
            }

            override fun willChangeBounds() = true
        }
        animation.duration = 250
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                expandable.isVisible = true
            }

            override fun onAnimationEnd(animation: Animation?) { }
            override fun onAnimationRepeat(animation: Animation?) { }
        })
        animation.interpolator = FastOutSlowInInterpolator()
        expandable.startAnimation(animation)
    }

    private fun collapse(collapsible: View) {
        val initialHeight = collapsible.measuredHeight
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    collapsible.isVisible = false
                } else {
                    collapsible.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    collapsible.requestLayout()
                }
            }

            override fun willChangeBounds() = true
        }
        animation.duration = 200
        animation.interpolator = FastOutSlowInInterpolator()
        collapsible.startAnimation(animation)
    }

    private fun rotate180(view: View, clockwise: Boolean) {
        val initialRotation = view.rotation
        val rotationAngle = if (clockwise) 180f else -180f
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                view.rotation = initialRotation + (rotationAngle * interpolatedTime)
            }

            override fun willChangeBounds() = false
        }
        animation.duration = if (clockwise) 250 else 200
        animation.interpolator = FastOutSlowInInterpolator()
        view.startAnimation(animation)
    }

    fun setData(history: List<RollHistory>) {
        val rolls = history.map { RollHistoryItem(it) }
        val diffResult = DiffUtil.calculateDiff(RollHistoryDiffCallback(rolls, this.rolls))
        diffResult.dispatchUpdatesTo(this)
        this.rolls = rolls
    }
}

class HistoryViewHolder(val binding: RowRollHistoryBinding): RecyclerView.ViewHolder(binding.root) {
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