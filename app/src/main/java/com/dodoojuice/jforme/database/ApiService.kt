package com.dodoojuice.jforme.database
import android.service.autofill.UserData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Query

interface ApiService {
    @GET("/login")
    fun loginUser(
        @Query("email") email: String,
        @Query("pw") password: String
    ): Call<Login>

    @GET("/")
    fun fetchUserData(): Call<List<UserData>>

    @GET("/board_list")
    fun fetchBoardData(): Call<List<Board>>

    @GET("/apidata")
    fun getApiData(@Query("id") id: Int): Call<apidata>


    @POST("/sign")
    suspend fun signUpUser(@Body request: SignupRequest): Response<UserData> // Adjust User type as needed

    @POST("/planUp_create")
    suspend fun planUp(@Body request: SignupRequest): Response<UserData> // Adjust User type as needed

}
object RetrofitInstance {
    private const val BASE_URL = "https://jforme.duckdns.org/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}

//----------------GET Data Class List
data class UserData_a(
    val user_email: String,
    val user_nick: String,
    val user_pw: String,
    val user_gender: Int,
    val user_birthday: String
)
data class Login(
    val user_nick: String)

data class Board(
    val b_id: Int,
    val user_id: Int,
    val b_title: String,
    val b_content: String?,
    val p_id: Int
)

data class apidata(
    val ID: Long,
    val stores: String?,
    val X: Float?,
    val Y: Float?,
    val road_address: String?,
    val place_url: String?,
    val phone: String?,
    val cate_name: String?,
    val cate_group_code: String?,
    val cate_group_name: String?
)

////----------------------------POST Data Class List
data class SignupRequest(
    val user_email: String,
    val user_nick: String,
    val user_pw: String,
    val user_gender: Int,
    val user_birthday: String
)
data class BoardupRequest(
    val user_id: Int,  // Change to match the actual type in your SQLAlchemy model
    val b_title: String,
    val b_content: String?,
    val p_id: Int  // Change to match the actual type in your SQLAlchemy model
)