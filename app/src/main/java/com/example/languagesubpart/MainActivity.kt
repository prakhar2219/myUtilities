package com.example.languagesubpart

import android.os.Bundle
import java.util.Locale

import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.languagesubpart.databinding.ActivityMainBinding
import com.google.mlkit.nl.languageid.LanguageIdentification

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.button.setOnClickListener {
            val langText: String = binding.editTextText.text.toString()
            if (langText.equals("")){
                Toast.makeText(this, "Please enter smoething", Toast.LENGTH_SHORT).show()
            }else {
                detectLanguage(langText)
            }
            }
        }


    private fun detectLanguage(langText: String) {
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(langText)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    binding.textView.text = "Can't identify language"
                } else {
                    val languageName = Locale(languageCode).displayLanguage
                    binding.textView.text = "Detected Language: $languageName"
                }
            }
            .addOnFailureListener {
                binding.textView.text = "Error: ${it.message}"
            }

        languageIdentifier.identifyPossibleLanguages(langText)
            .addOnSuccessListener { identifiedLanguages ->
                val result = StringBuilder("Possible Languages:\n")
                for (identifiedLanguage in identifiedLanguages) {
                    val language = identifiedLanguage.languageTag
                    val confidence = identifiedLanguage.confidence
                    val languageName = Locale(language).displayLanguage
                    result.append("$languageName (${(confidence * 100).toInt()}%)\n")
                }
                binding.textView.append("\n$result")
            }
            .addOnFailureListener {
                Log.e("LanguageDetection", "Error fetching possible languages", it)
            }
    }

}
