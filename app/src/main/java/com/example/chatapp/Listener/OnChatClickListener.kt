package com.example.chatapp.Listener

import com.example.chatapp.data.TalksData

interface OnChatClickListener {
    fun onChatItem(talksData: TalksData)
}