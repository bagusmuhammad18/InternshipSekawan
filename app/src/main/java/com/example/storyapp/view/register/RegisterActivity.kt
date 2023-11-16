package com.example.storyapp.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.storyapp.R
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Inisialisasi tombol register
        val btnRegister = binding.btnRegister
        btnRegister.isEnabled = false // Set awalnya ke false

        // Menambahkan TextChangedListener ke EditText untuk memeriksa input
        binding.edRegisterName.addTextChangedListener(watcher)
        binding.edRegisterEmail.addTextChangedListener(watcher)
        binding.edRegisterPassword.addTextChangedListener(watcher)

        btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            // Panggil fungsi register jika semua syarat terpenuhi
            if (isNameValid(name) && isEmailValid(email) && isPasswordValid(password)) {
                registerUser(name, email, password)
            }
        }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Tidak perlu diimplementasikan
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Tidak perlu diimplementasikan
        }

        override fun afterTextChanged(s: Editable?) {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val btnRegister = binding.btnRegister

            // Periksa input dan aktifkan tombol jika semua syarat terpenuhi
            btnRegister.isEnabled =
                isNameValid(name) && isEmailValid(email) && isPasswordValid(password)
        }
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun isEmailValid(email: String): Boolean {
        // Contoh sederhana untuk memeriksa format email
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    private fun registerUser(name: String, email: String, password: String) {
        val api = ApiConfig().getApiService()
        binding.progressBar.visibility = View.VISIBLE

        // Menggunakan coroutine untuk melakukan request HTTP di background thread
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.register(name, email, password)

                // Proses respons di thread utama
                runOnUiThread {
                    if (response.error == false) {
                        // Registrasi berhasil
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registrasi berhasil: ${response.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.progressBar.visibility = View.GONE

                    } else {
                        // Registrasi gagal
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registrasi gagal: ${response.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    // Error saat melakukan request HTTP
                    Toast.makeText(
                        this@RegisterActivity,
                        "Terjadi kesalahan: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}