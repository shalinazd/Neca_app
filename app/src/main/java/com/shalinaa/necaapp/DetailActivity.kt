package com.shalinaa.necaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.shalinaa.necaapp.model.DataItem
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_NEWS = "extra_news"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()
        fb_back_detail.setOnClickListener {
            startActivity(Intent(MainActivity.getLaunchService(this)))
        }
        val news = intent.getParcelableExtra<DataItem>("EXTRA_NEWS") as DataItem
        tv_title_detail.text = news.title.toString()
        tv_writer_detail.text = news.author.toString()
        tv_date_detail.text = news.publishedAt.toString()
        tv_content_detail.text = news.description.toString()
    }
}