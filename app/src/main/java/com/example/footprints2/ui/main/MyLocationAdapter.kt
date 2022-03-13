package com.example.footprints2.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.footprints2.databinding.MyLocationListItemBinding

/**
 * MyLocationアダプタ
 *
 * @property listener リスナー
 * @constructor 選択されたアイテムに対する動作
 */
class MyLocationAdapter(
    private val listener: (String) -> Unit
) : ListAdapter<String, MyLocationAdapter.ViewHolder>(callback) {
    /**
     * ビューホルダー
     *
     * @property binding バインディングデータ
     */
    class ViewHolder(
        private val binding: MyLocationListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * リストアイテムの登録
         *
         * @param date 画面に表示する日付
         */
        fun bindTo(date: String) {
            binding.apply {
                dateAndTime.text = date
            }
        }
    }

    /**
     * ビューホルダーの作成
     *
     * @param parent ビューグループ
     * @param viewType //TODO わからん
     * @return ビューホルダー
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MyLocationListItemBinding.inflate(
            inflater,
            parent,
            false
        )

        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val date = getItem(position)
            listener(date)
        }

        return viewHolder
    }

    /**
     * ビューホルダーへのMuLocation登録
     *
     * @param holder ビューホルダー
     * @param position position番目のアイテム
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = getItem(position)
        holder.bindTo(date)
    }

    companion object {
        /**
         * 差分検知のためのコールバック。
         */
        private val callback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

}