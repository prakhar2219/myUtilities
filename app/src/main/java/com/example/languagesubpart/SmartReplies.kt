package com.example.languagesubpart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import com.google.mlkit.nl.smartreply.*

class SmartReplies : AppCompatActivity() {
    private lateinit var messageInput: EditText
    private lateinit var generateRepliesButton: Button
    private lateinit var repliesOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_smart_replies)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        messageInput = findViewById(R.id.messageInput)
        generateRepliesButton = findViewById(R.id.generateRepliesButton)
        repliesOutput = findViewById(R.id.repliesOutput)

        generateRepliesButton.setOnClickListener {
            val userMessage = messageInput.text.toString()
            if (userMessage.isNotEmpty()) {
                generateSmartReplies(userMessage)
            } else {
                Toast.makeText(this, "Enter a message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateSmartReplies(userMessage: String) {
        val conversation = mutableListOf<TextMessage>()

        // Add user's message as the latest message in the conversation
        conversation.add(TextMessage.createForLocalUser(userMessage, System.currentTimeMillis()))

        val smartReplyGenerator = SmartReply.getClient()
        smartReplyGenerator.suggestReplies(conversation)
            .addOnSuccessListener { result ->
                if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS) {
                    val replies = result.suggestions.map { it.text }
                    repliesOutput.text = "Smart Replies:\n" + replies.joinToString("\n")
                } else {
                    repliesOutput.text = "No smart replies available."
                }
            }
            .addOnFailureListener {
                repliesOutput.text = "Error: ${it.message}"
            }
    }
}


//
//val conversation = mutableListOf<TextMessage>()
//
//// Message from another user (Remote)
//conversation.add(
//TextMessage.createForRemoteUser("Hey, how's it going?", System.currentTimeMillis(), "user1")
//)
//
//// Message from the local user (You)
//conversation.add(
//TextMessage.createForLocalUser("I'm good! What about you?", System.currentTimeMillis())
//)
//
//// Another message from a different user (Group chat scenario)
//conversation.add(
//TextMessage.createForRemoteUser("I'm doing great too!", System.currentTimeMillis(), "user2")
//)
//
//// Send conversation to Smart Reply
//val smartReplyGenerator = SmartReply.getClient()
//smartReplyGenerator.suggestReplies(conversation)
//.addOnSuccessListener { result ->
//    if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS) {
//        val replies = result.suggestions.map { it.text }
//        println("Smart Replies: $replies")
//    }
//}






