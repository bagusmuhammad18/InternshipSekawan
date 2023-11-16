package com.example.storyapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.ErrorResponse
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.view.main.MainActivity
import com.example.storyapp.view.register.RegisterActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailEditText = binding.edLoginEmail
        val passwordEditText = binding.edLoginPassword
        val masukButton = binding.masukButton
        val daftarButton = binding.daftarButton

        // Menambahkan TextWatcher untuk memeriksa input
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isEmailValid = emailEditText.isValid
                val isPasswordValid = s?.length ?: 0 >= 8
                masukButton.isEnabled = isEmailValid && isPasswordValid
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Tidak digunakan
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tidak digunakan
            }
        })

        // Menambahkan OnClickListener untuk tombol "Masuk"
        masukButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Atur ProgressBar menjadi terlihat
            binding.progressBar.visibility = View.VISIBLE

            // Memanggil fungsi login
            loginUser(email, password)
        }

        // Menambahkan OnClickListener untuk tombol "Daftar"
        daftarButton.setOnClickListener {
            // Redirect to RegisterActivity when "Daftar" button is clicked
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        val api = ApiConfig().getApiService()

        // Menggunakan coroutine untuk melakukan request HTTP di background thread
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.login(email, password)

                // Proses respons di thread utama
                runOnUiThread {
                    if (response.error == false) {
                        // Login berhasil
                        val userId = response.loginResult?.userId
                        val name = response.loginResult?.name
                        val token = response.loginResult?.token

//                        Toast.makeText(this@LoginActivity, "Login berhasil. UserID: $userId, Name: $name", Toast.LENGTH_SHORT).show()

                        // Simpan token atau data login sesuai kebutuhan
                        if (token != null) {
                            PreferenceManager.saveToken(this@LoginActivity, token)
                        }
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)

//                        finish()
                        finishAffinity()
                    } else {
                        // Login gagal
                        val errorMessage = response.message ?: "Terjadi kesalahan"
                        Toast.makeText(this@LoginActivity, "Login gagal: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                    binding.progressBar.visibility = View.GONE
                }
            } catch (e: HttpException) {
                // Terjadi kesalahan HTTP
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message ?: "Terjadi kesalahan"
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Terjadi kesalahan: $errorMessage", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE

                }
            } catch (e: Exception) {
                // Error lainnya
                val errorMessage = e.message ?: "Terjadi kesalahan"
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Terjadi kesalahan: $errorMessage", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE

                }
            }
        }
    }
}
