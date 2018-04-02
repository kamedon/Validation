package com.kamedon.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.kamedon.validation.Validation

class MainActivity : AppCompatActivity() {

    val registerBtn by lazy {
        findViewById<View>(R.id.btnRegister)
    }

    val nameEdit by lazy {
        findViewById<EditText>(R.id.userNameEdit)
    }

    val ageEdit by lazy {
        findViewById<EditText>(R.id.userAgeEdit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val validation = Validation<User> {
            "name"{
                be { !name.isEmpty() } not "name: 5 characters or more"
            }
            "age"{
                be { age >= 20 } not "age: Over 20 years old"
            }
        }

        registerBtn.setOnClickListener {
            val ageText = ageEdit.text?.toString() ?: ""
            val age = if (ageText.isBlank()) {
                0
            } else {
                ageText.toInt()
            }
            val user = User(nameEdit.text?.toString() ?: "", age, validation)
            val errors = user.validate()
            if (errors.isEmpty()) {
                Toast.makeText(applicationContext, "valid data!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "errors: $errors", Toast.LENGTH_SHORT).show()
            }

        }


    }
}


class User(val name: String, val age: Int, private val validation: Validation<User>) {
    fun validate() = validation.validate(this)
}
