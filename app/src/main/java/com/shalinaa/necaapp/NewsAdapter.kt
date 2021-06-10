package com.shalinaa.necaapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shalinaa.necaapp.model.DataItem
import kotlinx.android.synthetic.main.news_item.view.*
import org.jetbrains.anko.intentFor

class NewsAdapter (var context: Context, var listNews: List<DataItem?>?) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder (view : View) :RecyclerView.ViewHolder(view){
        fun bind (news: DataItem){
            with(itemView){
                tv_title_item.text = news.title
                tv_date_item.text = news.publishedAt
                itemView.setOnClickListener{
                    itemView.context?.startActivity(
                        itemView.context.intentFor<DetailActivity>(
                            "EXTRA_NEWS" to news
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listNews!!.size

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(listNews?.get(position)!!)
    }
}