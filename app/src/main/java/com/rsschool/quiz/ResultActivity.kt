package com.rsschool.quiz

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rsschool.quiz.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var userObject: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Чтобы создать объект класса ResultProfileBinding, надо вызвать статический метод inflate()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the Intent that started this activity and extract the string
        userObject = intent.getSerializableExtra(EXTRA_MESSAGE) as User

        // Capture the layout's TextView and set the string as its text
        binding.textResult.apply {
            text = userObject.resultMessage
        }
    }

    // android:onClick
    fun sendEmail(view: View) {

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:" + userObject.email) // only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT, userObject.resultMessage)
            putExtra(Intent.EXTRA_TEXT, userObject.resultQuiz)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    // android:onClick
    fun restartQuiz(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // android:onClick
    fun exitApp(view: View) {
        finish()
    }
}