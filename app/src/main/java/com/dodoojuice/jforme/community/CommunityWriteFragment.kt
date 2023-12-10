package com.dodoojuice.jforme.community

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.dodoojuice.jforme.R
import com.dodoojuice.jforme.databinding.FragmentCommunityWriteBinding



class CommunityWriteFragment(val position : Int) : Fragment() {
    lateinit var binding : FragmentCommunityWriteBinding
    private val communityViewModel: CommunityWriteViewModel by activityViewModels()
    private val PERMISSION_CODE = 1000
    var uri : Uri ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_community_write, container, false)
        binding.lifecycleOwner = this
        binding.communityViewModel = communityViewModel
        binding.CountView1.text = position.plus(1).toString()

        binding.photoView.setOnClickListener{
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //스토리지 읽기 권한이 허용이면 커스텀 앨범 띄워주기
                    //갤러리 호출
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    activityResult.launch(intent)
                    //권한 있을 경우 : PERMISSION_GRANTED
                    //권한 없을 경우 : PERMISSION_DENIED
                }

                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_IMAGES) -> {
                    //권한을 명시적으로 거부한 경우 : ture
                    //다시 묻지 않음을 선택한 경우 : false
                    //다이얼로그를 띄워 권한 팝업을 해야하는 이유 및 권한팝업을 허용하여야 접근 가능하다는 사실을 알려줌
                    showPermissionAlertDialog()
                }

                else -> {
                    //권한 요청
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                        PERMISSION_CODE
                    )
                }
            }
        }
        return binding.root
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한 허용클릭
                    //TODO()앨범으로 이동시키기!
                } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_MEDIA_IMAGES)) {
                    //권한 처음으로 거절 했을 경우
                    //한번더 권한 요청
                    showPermissionAlertDialog()
                } else {
                    //권한 두번째로 거절 한 경우 (다시 묻지 않음)
                    //설정 -> 권한으로 이동하는 다이얼로그
                    goSettingActivityAlertDialog()
                }
            }
        }
    }

    //갤러리 호출 결과 가져오기 -> db에 어떤 형식으로 저장하는지 모름.
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        //결과 코드 ok, 결과값 null 아니면
        if(it.resultCode == RESULT_OK && it.data != null){
            //값 담기
            uri = it.data!!.data!!
            val imgView = binding.photoView
            //화면에 보여주기
            Glide.with(this)
                .load(uri)//이미지
                .into(imgView) //보여줄 위치
        }
    }
    private fun showPermissionAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("권한 승인이 필요합니다.")
            .setMessage("사진을 선택 하시려면 권한이 필요합니다.")
            .setPositiveButton("허용하기") { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    PERMISSION_CODE
                )
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }
    private fun goSettingActivityAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("권한 승인이 필요합니다.")
            .setMessage("앨범에 접근 하기 위한 권한이 필요합니다.\n권한 -> 사진 및 동영상 -> 허용")
            .setPositiveButton("허용하러 가기") { _, _ ->
                val goSettingPermission = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                goSettingPermission.data = Uri.parse("package:com.dodoojuice.jforme")
                startActivity(goSettingPermission)
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    fun getTextItem(): CommunityWriteItem? {
        if(uri != null){
            return CommunityWriteItem(uri!!, binding.mtv.text.toString())}
        else return null
    }
}