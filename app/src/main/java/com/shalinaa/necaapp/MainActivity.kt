package com.shalinaa.necaapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.shalinaa.necaapp.model.ResponseNews
import com.shalinaa.necaapp.service.RetroConfig
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var refUsers : DatabaseReference? = null
    var firebaseUser : FirebaseUser? = null
    val date = getCurrentDateTime()

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun Date.toString(format : String, locale : Locale = Locale.getDefault()):String{
        val formatter = SimpleDateFormat (format, locale)
        return formatter.format(this)
    }

    companion object{
        fun getLaunchService (from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        ib_profile_main.setOnClickListener(this)
        getNews()
    }

    private fun getNews() {
        val country = "au,-us"
        val apiKey = "1deab2419c0c6b49ec2de1aa210f3e73"
        val categories = "general,-health"

        val loading = ProgressDialog.show(this, "Request Data", "Loading. .")
        RetroConfig.getInstance().getNewsData(apiKey,categories,country).enqueue(
            object : Callback<ResponseNews> {
                override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                    Log.d("Response", "Failed : " + t.localizedMessage)
                    loading.dismiss()
                }

                override fun onResponse(
                    call: Call<ResponseNews>,
                    response: Response<ResponseNews>
                ) {
                    Log.d("Response", "Success" + response.body()?.data)
                    loading.dismiss()
                    if(response.isSuccessful){
                        val status = response.body()?.pagination
                        if (status!!.equals("ok")){
                            Toast.makeText(this@MainActivity, "Data sucess !", Toast.LENGTH_SHORT).show()
                            val newsData = response.body()?.data
                            val newsAdapter = NewsAdapter(this@MainActivity, newsData)
                            rv_main.adapter = newsAdapter
                            rv_main.layoutManager = LinearLayoutManager(this@MainActivity)

                            val dataHighlight = response.body()

                            tv_highlight.text = dataHighlight?.data?.component2()?.title
                            tv_date_highlight.text = dataHighlight?.data?.component2()?.publishedAt
                        }else{
                            Toast.makeText(this@MainActivity, "Data Failed !", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        )
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.ib_profile_main -> startActivity(Intent(ProfileActivity.getLaunchService(this)))
        }
    }
}