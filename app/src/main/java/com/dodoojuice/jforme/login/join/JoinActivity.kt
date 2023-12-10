package com.dodoojuice.jforme.login.join

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.ActivityJoinBinding
import com.dodoojuice.jforme.main.MainGpsViewModel

class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    private val joinViewModel : JoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }
}
class JoinViewModel: ViewModel() {

    private var _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email
    private var _pw = MutableLiveData<String>()
    val pw: LiveData<String>
        get() = _pw
    private var _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname
    private var _birthday = MutableLiveData<String>()
    val birthday: LiveData<String>
        get() = _birthday
    private var _sex = MutableLiveData<Int>()
    val sex: LiveData<Int>
        get() = _sex


    init {
        _email.value = ""
        _pw.value = ""
        _nickname.value = ""
        _birthday.value = ""
        _sex.value = 0
    }

    fun setEmail(email : String) {
        _email.value = email
    }
    fun setPassword(pw : String) {
        _pw.value = pw
    }
    fun setNickname(nickname : String) {
        _nickname.value = nickname
    }
    fun setBirthday(birthday : String) {
        _birthday.value = birthday
    }
    fun setSex(sex : Int) {
        _sex.value = sex
    }
}