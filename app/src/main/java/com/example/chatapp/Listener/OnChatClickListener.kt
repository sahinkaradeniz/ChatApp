package com.example.chatapp.Listener

import com.example.chatapp.data.Chat

interface OnChatClickListener {
    fun onChatItem(chat: Chat)
}