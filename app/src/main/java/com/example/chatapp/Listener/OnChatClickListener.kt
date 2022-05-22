package com.example.chatapp.Activity

import com.example.chatapp.data.TalksData

interface OnChatClickListener {
    fun onChatItem(talksData: TalksData)
}