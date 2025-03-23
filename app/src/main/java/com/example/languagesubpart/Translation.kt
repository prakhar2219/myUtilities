package com.example.languagesubpart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*


class Translation : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var sourceLanguageSpinner: Spinner
    private lateinit var targetLanguageSpinner: Spinner
    private lateinit var translateButton: Button
    private lateinit var translatedText: TextView
    private lateinit var languageMap: Map<String, String>

//    private val languageMap = mapOf(
//        "English" to TranslateLanguage.ENGLISH,
//        "Spanish" to TranslateLanguage.SPANISH,
//        "French" to TranslateLanguage.FRENCH,
//        "German" to TranslateLanguage.GERMAN,
//        "Hindi" to TranslateLanguage.HINDI,
//        "Chinese" to TranslateLanguage.CHINESE
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_translation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editText = findViewById(R.id.editText)
        sourceLanguageSpinner = findViewById(R.id.sourceLanguageSpinner)
        targetLanguageSpinner = findViewById(R.id.targetLanguageSpinner)
        translateButton = findViewById(R.id.translateButton)
        translatedText = findViewById(R.id.translatedText)
        setupLanguageMap()

        setupSpinners()

        translateButton.setOnClickListener {
            val sourceLang = sourceLanguageSpinner.selectedItem.toString()
            val targetLang = targetLanguageSpinner.selectedItem.toString()
            val textToTranslate = editText.text.toString()

            if (textToTranslate.isEmpty()) {
                Toast.makeText(this, "Enter text to translate", Toast.LENGTH_SHORT).show()
            } else {
                translateText(textToTranslate, languageMap[sourceLang]!!, languageMap[targetLang]!!)
            }
        }
    }

    private fun setupLanguageMap() {
        val supportedLanguages = TranslateLanguage.getAllLanguages()
        languageMap = supportedLanguages.associateWith { Locale(it).displayLanguage }
            .entries.associate { (code, name) -> name to code }
    }

    private fun setupSpinners() {
        val languageNames = languageMap.keys.sorted()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languageNames)
        sourceLanguageSpinner.adapter = adapter
        targetLanguageSpinner.adapter = adapter
    }

    private fun translateText(text: String, sourceLang: String, targetLang: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLang)
            .setTargetLanguage(targetLang)
            .build()

        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { translatedText.text = it }
                    .addOnFailureListener { translatedText.text = "Error: ${it.message}" }
            }
            .addOnFailureListener {
                translatedText.text = "Failed to download language model."
            }
    }
}













