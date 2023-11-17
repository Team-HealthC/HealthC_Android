package com.healthc.app.presentation.detection.object_detection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.healthc.app.databinding.ItemObjectDetectionBinding
import com.healthc.data.model.local.detection.ObjectDetectionResult

class ObjectDetectionAdapter(
    private val classes: List<String>,
    private val onClick: (String) -> Unit,
): ListAdapter<ObjectDetectionResult, ObjectDetectionAdapter.ObjectDetectionViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectDetectionViewHolder {
        return ObjectDetectionViewHolder(
            ItemObjectDetectionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ObjectDetectionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ObjectDetectionViewHolder(
        private val binding: ItemObjectDetectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(objectDetectionResult: ObjectDetectionResult) {
            val detectedObject = classes[objectDetectionResult.classIndex]
            binding.tvODItemObject.text = detectedObject

            val score = objectDetectionResult.score * 100
            binding.tvODItemScore.text = "${String.format("%.2f", score)}%"

            binding.cdODItem.setOnClickListener {
                onClick(detectedObject)
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ObjectDetectionResult>() {
            override fun areItemsTheSame(oldItem: ObjectDetectionResult, newItem: ObjectDetectionResult): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ObjectDetectionResult, newItem: ObjectDetectionResult): Boolean {
                return oldItem == newItem
            }
        }
    }
}