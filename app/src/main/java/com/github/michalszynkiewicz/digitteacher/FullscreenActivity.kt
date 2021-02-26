package com.github.michalszynkiewicz.digitteacher

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.random.Random

// TODO: sometimes letters make the buttons too wide
// TODO: digits?
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    private lateinit var tts: TextToSpeech

    var locked = false

    private var correctButton = 1
    private var correctDigit = 1

    private var attemptCount = 0
    private var successCount = 0

    val buttons = arrayOf(R.id.button1, R.id.button2, R.id.button3, R.id.button4);


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)


        buttons.forEachIndexed { idx, value -> initializeButton(idx, value) }

        tts = TextToSpeech(applicationContext) {
            if (it != TextToSpeech.ERROR) {
                tts.language = Locale.forLanguageTag("pl")
                tts.setPitch(0.8f) // mstodo
                tts.setSpeechRate(1.0f)
                initNewTask()
            } else {
                Toast.makeText(this, "Usługa czytania tekstu jest niedostępna", Toast.LENGTH_LONG)
                    .show()
            }
        }

        initNewTask()
    }

    override fun onPause() {
        super.onPause()

        tts.stop();
        tts.shutdown();
    }

    @SuppressLint("SetTextI18n")
    private fun initializeButton(number: Int, buttonId: Int) {
        val clickButton: Button = findViewById(buttonId);
        clickButton.transformationMethod = null
        clickButton.setOnClickListener {
            if (!locked) {
                locked = true
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                );
                val success = correctButton == number;
                clickButton.setBackgroundColor(clickButton.context.getColor(if (success) R.color.green else R.color.red))
                attemptCount++;
                successCount += if (success) 1 else 0;
                val status = findViewById<TextView>(R.id.status)
                status.setBackgroundColor(getColor(R.color.black))
                status.text = """Dobrze $successCount z $attemptCount"""

                var timeToNext: Long = 2000
                if (success) {
                    tts.speak("Brawo!", TextToSpeech.QUEUE_ADD, null, null);
                } else {
                    val properButton = findViewById<Button>(buttons[correctButton])
                    properButton.setBackgroundColor(getColor(R.color.light_blue_600))
                    tts.speak(
                        "To niestety nie jest dobra odpowiedź",
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    );
                    timeToNext = 4000
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    initNewTask()
                }, timeToNext)
            }
        }
    }

    private fun initNewTask() {
        correctDigit = Random.nextInt(10);

        val dots = findViewById<DotsView>(R.id.dots)
        dots.setCount(correctDigit)

        correctButton = Random.nextInt(4)

        val wrongDigits: List<Int> = wrongDigits()
        var wrongDigitIdx = 0
        for (i in 0..3) {
            val button: Button = findViewById(buttons[i])
            button.setBackgroundColor(button.context.getColor(R.color.beige))
            if (i == correctButton) {
                button.text = """$correctDigit"""
            } else {
                button.text = """${wrongDigits[wrongDigitIdx++]}"""
            }
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        locked = false
    }

    private fun wrongDigits(): List<Int> {
        val usedDigits = mutableSetOf(correctDigit)
        val result: MutableList<Int> = mutableListOf()

        for (i in 0..3) {
            while (true) {
                val digit = Random.nextInt(10)
                if (!usedDigits.contains(digit)) {
                    usedDigits.add(digit)
                    result.add(digit)
                    break
                }
            }
        }

        return result
    }

}