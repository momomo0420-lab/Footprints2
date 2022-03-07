package com.example.footprints2.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.footprints2.databinding.MyLocationListItemBinding
import com.example.footprints2.model.repository.database.MyLocation
import com.example.footprints2.model.repository.database.convertDateAndTimeToMyFormat

/**
 * MyLocationアダプタ
 *
 * @property listener リスナー
 * @constructor 選択されたアイテムに対する動作
 */
class MyLocationAdapter(
    private val listener: (MyLocation) -> Unit
) : ListAdapter<MyLocation, MyLocationAdapter.ViewHolder>(callback) {
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
         * @param myLocation 画面に表示するMyLocationのアイテム
         */
        fun bindTo(myLocation: MyLocation) {
            binding.apply {
                address.text = myLocation.address
                dateAndTime.text = myLocation.convertDateAndTimeToMyFormat()
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
            val myLocation = getItem(position)
            listener(myLocation)
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
        val myLocation = getItem(position)
        holder.bindTo(myLocation)
    }

    companion object {
        /**
         * 差分検知のためのコールバック。
         */
        private val callback = object : DiffUtil.ItemCallback<MyLocation>() {
            override fun areItemsTheSame(oldItem: MyLocation, newItem: MyLocation): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: MyLocation, newItem: MyLocation): Boolean {
                return oldItem.address == newItem.address
            }
        }
    }

}