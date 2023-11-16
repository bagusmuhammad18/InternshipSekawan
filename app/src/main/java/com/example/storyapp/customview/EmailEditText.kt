package com.example.storyapp.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.util.regex.Pattern

class EmailEditText : AppCompatEditText {

    private var emailPattern: Pattern
    var isValid: Boolean = true

    constructor(context: Context) : super(context) {
        emailPattern = android.util.Patterns.EMAIL_ADDRESS
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        emailPattern = android.util.Patterns.EMAIL_ADDRESS
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        emailPattern = android.util.Patterns.EMAIL_ADDRESS
        init()
    }

    private fun init() {
        // Add a TextWatcher to validate email input
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Check if the input matches the email pattern
                isValid = emailPattern.matcher(s).matches()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }
        })

        // Set an OnFocusChangeListener to handle errors when the focus is lost
        setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!isValid) {
                    // Handle invalid email input when focus is lost, e.g., set an error message
                    error = "Invalid email address"
                } else {
                    // Clear the error message when focus is lost if the email is valid
                    error = null
                }
            }
        }
    }
}
