package com.example.languagesubpart

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.languagesubpart.databinding.ActivityStartBinding

class startActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
binding.replies.setOnClickListener {
    val intent=Intent(this,SmartReplies::class.java)
    startActivity(intent)
}
        binding.objectDetection.setOnClickListener {
            val intent=Intent(this,DetectObjects::class.java)
            startActivity(intent)
        }
        binding.translation.setOnClickListener {
            val intent=Intent(this,Translation::class.java)
            startActivity(intent)
        }
        binding.Language.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}