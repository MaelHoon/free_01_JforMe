package com.dodoojuice.jforme.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.bumptech.glide.Glide
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.ActivitySettingBinding

class NestedSettingPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

        // 전체 푸시 알림 설정에 따라 나머지 알림 항목 체크 상태 변경
        val notificationAllPreference = findPreference<SwitchPreference>("notification_all")
        notificationAllPreference?.setOnPreferenceChangeListener { _, newValue ->
            val isChecked = newValue as Boolean

            // 나머지 알림 항목들의 체크 상태 변경
            findPreference<SwitchPreference>("notification_comment")?.isChecked = isChecked
            findPreference<SwitchPreference>("notification_like")?.isChecked = isChecked
            findPreference<SwitchPreference>("notification_follow")?.isChecked = isChecked
            findPreference<SwitchPreference>("notification_event")?.isChecked = isChecked

            true
        }
        //비밀번호 수정 클릭
        val passwordChangePreference = findPreference<Preference>("password_change")
        passwordChangePreference?.setOnPreferenceClickListener {
            // 다이얼로그 표시
            showChangePasswordDialog()
            true
        }
        //계정 탈퇴 클릭
        val accountDeletePreference = findPreference<Preference>("account_delete")
        accountDeletePreference?.setOnPreferenceClickListener {
            // 다이얼로그 표시: 현재 비밀번호 확인
            showConfirmAccountDeletionDialog()
            true
        }
        //로그아웃 클릭
        val logoutPreference = findPreference<Preference>("logout")
        logoutPreference?.setOnPreferenceClickListener {
            showLogoutConfirmationDialog()
            true
        }
        //닉네임 변경
        val nicknamePreference = findPreference<Preference>("nickname")
        nicknamePreference?.setOnPreferenceClickListener {
            // TODO: 닉네임 변경 다이얼로그 띄우기
            showNicknameChangeDialog()
            true
        }
        //소개글 변경
        val introductionPreference = findPreference<Preference>("introduction")
        introductionPreference?.setOnPreferenceClickListener {
            // TODO: 소개글 변경 다이얼로그 띄우기
            showIntroductionChangeDialog()
            true
        }
        //문의하기
        val inquiryPreference = findPreference<Preference>("inquiry")
        inquiryPreference?.setOnPreferenceClickListener {
            showInquiryDialog()
            true
        }
    }


    //비밀번호 수정
    private fun showChangePasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_setting_editpassword, null)
        val currentPasswordEditText = dialogView.findViewById<EditText>(R.id.current_password)
        val newPasswordEditText = dialogView.findViewById<EditText>(R.id.new_password)
        val confirmPasswordEditText = dialogView.findViewById<EditText>(R.id.confirm_password)
        val passwordMismatchTextView =
            dialogView.findViewById<TextView>(R.id.textView_password_mismatch)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("비밀번호 수정")
            .setView(dialogView)
            .setPositiveButton("확인") { _, _ ->
                val currentPassword = currentPasswordEditText.text.toString()
                val newPassword = newPasswordEditText.text.toString()
                val confirmPassword = confirmPasswordEditText.text.toString()

                // TODO: 비밀번호 변경 로직 구현
                // 여기에서 FirebaseAuth 등을 사용하여 비밀번호를 변경하는 로직을 구현해야 합니다.

                confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        val newPassword = newPasswordEditText.text.toString()
                        val confirmPassword = s.toString()
                        if (newPassword == confirmPassword) {
                            passwordMismatchTextView.visibility = View.GONE
                        } else {
                            passwordMismatchTextView.visibility = View.VISIBLE
                        }
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
            }
            .setNegativeButton("취소", null)
            .create()

        dialog.show()
    }

    //계정 탈퇴
    private fun showConfirmAccountDeletionDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("계정 탈퇴 확인")
            .setMessage("정말로 계정을 탈퇴하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                // TODO: 계정 탈퇴 로직을 구현
                // 여기에서 FirebaseAuth 등을 사용하여 계정 탈퇴를 구현해야 합니다.
            }
            .setNegativeButton("취소", null)
            .create()

        dialog.show()
    }

    //로그아웃
    private fun showLogoutConfirmationDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("로그아웃 확인")
            .setMessage("정말로 로그아웃하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                // TODO: 로그아웃 로직을 구현
                // 여기에서 FirebaseAuth 등을 사용하여 로그아웃을 구현해야 합니다.
            }
            .setNegativeButton("취소", null)
            .create()

        dialog.show()
    }

    //프로필 변경
    private fun showProfileImageChangeDialog() {
        // TODO: 프로필 사진 변경 다이얼로그 띄우고 앨범 열기 등의 로직 구현
    }

    //닉네임 변경
    private fun showNicknameChangeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_setting_editnickname, null)
        val changenickname = dialogView.findViewById<EditText>(R.id.changenickname)


        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("닉네임 설정")
            .setView(dialogView)
            .setPositiveButton("수정") { _, _ ->
                // TODO: 닉네임 변경 다이얼로그 띄우고 변경된 닉네임 처리하는 로직 구현
                val changenickname = changenickname.text.toString()
            }
            .setNegativeButton("취소", null)
            .create()
        dialog.show()
    }

    //소개글 변경
    private fun showIntroductionChangeDialog() {
        // TODO: 소개글 변경 다이얼로그 띄우고 변경된 소개글 처리하는 로직 구현
    }

    // 문의하기
    private fun showInquiryDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("문의하기")
            .setMessage("문의 사항은 jy04169@gmail.com\n메일 부탁드립니다.")
            .setPositiveButton("확인") { _, _ ->

            }
            .create()

        dialog.show()
    }
}
class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.recycler_view, NestedSettingPreferenceFragment())
            .commit()

        //버튼 이벤트
        binding.changephoto.setOnClickListener {

            //갤러리 호출
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }
    }//onCreate

    //결과 가져오기
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

        //결과 코드 ok, 결과값 null 아니면
        if(it.resultCode == RESULT_OK && it.data != null){
            //값 담기
            val uri = it.data!!.data

            //화면에 보여주기
            Glide.with(this)
                .load(uri)//이미지
                .into(binding.profileImg) //보여줄 위치

        }
    }
}



