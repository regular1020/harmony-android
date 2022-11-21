package com.harmony.harmonyAndroid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harmony.harmonyAndroid.activity.MessageDetailActivity
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.databinding.MessageItemBinding
import com.harmony.harmonyAndroid.viewmodel.MessageBoxViewModel

class MessageAdapter(val context: Context, val itemList: MutableList<Message>, val messageBoxViewModel: MessageBoxViewModel) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MessageViewHolder(val context: Context, private val binding: MessageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.message = item

            binding.root.setOnClickListener{
                Intent(context, MessageDetailActivity::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}