package com.dodoojuice.jforme.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dodoojuice.jforme.MainActivity
import com.dodoojuice.jforme.database.ApiService
import com.dodoojuice.jforme.database.Login
import com.dodoojuice.jforme.database.RetrofitInstance
import com.dodoojuice.jforme.database.UserData_a
import com.dodoojuice.jforme.databinding.ActivityLoginBinding
import com.dodoojuice.jforme.login.join.JoinActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = RetrofitInstance.apiService
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener{
            /*val email = binding.email.text.toString()
            val password = binding.pw.text.toString()
            lifecycleScope.launch {
                performLogin(email, password)
            }*/
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        binding.jointext.setOnClickListener{
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin(email: String, password: String) {
        val call: Call<Login> = apiService.loginUser(email, password)

        call.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.isSuccessful) {
                    // Successful login
                    val login = response.body()
                    Log.d("LoginResponse", "Response: $login")
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    // Process the login response
                } else {
                    // Handle login error
                    Log.e("LoginResponse", "Error: ${response.code()}")
                    Toast.makeText(applicationContext, "회원 정보가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                    // Display an error message to the user
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                // Handle network or other errors
                Log.e("LoginResponse", "Error: ${t.message}")
                Toast.makeText(applicationContext, "회원 정보가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                // Display an error message to the user
            }
        })
    }


}