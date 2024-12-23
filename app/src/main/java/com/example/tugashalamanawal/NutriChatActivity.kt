package com.example.tugashalamanawal

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class NutriChatActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val url = "https://api.openai.com/v1/chat/completions"
    private val apiKey = BuildConfig.OPENAI_API_KEY // Use BuildConfig to include the API key securely

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nutri_chat)

        val inputMessage = findViewById<EditText>(R.id.input_message)
        val chatScroll = findViewById<ScrollView>(R.id.chat_scroll)
        val chatContainer = findViewById<LinearLayout>(R.id.chat_container)
        val sendButton = findViewById<ImageView>(R.id.send_button)
        val backButton = findViewById<ImageView>(R.id.back_button)

        // Back button functionality
        backButton.setOnClickListener {
            finish()
        }

        // Send button functionality
        sendButton.setOnClickListener {
            val userMessage = inputMessage.text.toString()
            if (userMessage.isNotEmpty()) {
                addChatBubble(userMessage, isUser = true, chatContainer, chatScroll)
                inputMessage.text.clear()
                getResponse(userMessage) { response ->
                    runOnUiThread {
                        addChatBubble(response, isUser = false, chatContainer, chatScroll)
                    }
                }
            }
        }
    }

    private fun getResponse(userMessage: String, callback: (String) -> Unit) {
        if (apiKey.isEmpty()) {
            throw IllegalStateException("API key is missing.")
        }

        val jsonBody = JSONObject().apply {
            put("model", "gpt-3.5-turbo")
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", "You are a chatbot that provides healthy snacks and meals.")
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", userMessage)
                })
            })
        }

        val requestBody = jsonBody.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback("Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseJson = JSONObject(responseBody.string())
                    val reply = responseJson.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                    callback(reply)
                } ?: callback("No response from server.")
            }
        })
    }

    private fun addChatBubble(
        message: String,
        isUser: Boolean,
        chatContainer: LinearLayout,
        chatScroll: ScrollView
    ) {
        val textView = TextView(this).apply {
            text = if (isUser) "You: $message" else "Bot: $message"
            setPadding(16, 8, 16, 8)
            textSize = 16f
        }
        chatContainer.addView(textView)
        chatScroll.post { chatScroll.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}
