package com.example.blogspot.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.blogspot.Add_Edit_articles
import com.example.blogspot.Article_Post_show
import com.example.blogspot.R
import com.example.blogspot.Utills.constrants
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
            var articleid:Int=0
            var offset = 0
            var limit = 10
            settxtviewArticles(offset, limit)
            root.btnprevarticles.setOnClickListener {
                if (offset >= 10) {
                    offset -= 10
                    limit -= 10
                    headingArticles.text = "Articles"
                    settxtviewArticles(offset, limit)
                }
            }
            root.btnnextarticles.setOnClickListener {
                if (offset >= 0 && headingArticles.text == "Articles") {
                    offset += 10
                    limit += 10
                    settxtviewArticles(offset, limit)
                }
            }
            root.btnaddarticle.setOnClickListener {
                val intent = Intent(activity, Add_Edit_articles::class.java)
                intent.putExtra("articleId", 0)
                startActivity(intent)
            }
            run {
                root.article1.setOnClickListener {
                    articleid=articleId1.text.toString().toInt()
                    movetoposts(articleId1.text.toString().toInt())
                }
                root.article2.setOnClickListener {
                    articleid=articleId2.text.toString().toInt()
                    movetoposts(articleId2.text.toString().toInt())
                }
                root.article3.setOnClickListener {
                    articleid=articleId3.text.toString().toInt()
                    movetoposts(articleId3.text.toString().toInt())
                }
                root.article4.setOnClickListener {
                    articleid=articleId4.text.toString().toInt()
                    movetoposts(articleId4.text.toString().toInt())
                }
                root.article5.setOnClickListener {
                    articleid=articleId5.text.toString().toInt()
                    movetoposts(articleId5.text.toString().toInt())
                }
                root.article6.setOnClickListener {
                    articleid=articleId6.text.toString().toInt()
                    movetoposts(articleId6.text.toString().toInt())
                }
                root.article7.setOnClickListener {
                    articleid=articleId7.text.toString().toInt()
                    movetoposts(articleId7.text.toString().toInt())
                }
                root.article8.setOnClickListener {
                    articleid=articleId8.text.toString().toInt()
                    movetoposts (articleId8.text.toString().toInt())
                }
                root.article9.setOnClickListener {
                    articleid=articleId9.text.toString().toInt()
                    movetoposts(articleId9.text.toString().toInt())
                }
                root.article10.setOnClickListener {
                    articleid=articleId10.text.toString().toInt()
                    movetoposts(articleId10.text.toString().toInt())
                }
            }//click listners of text views

        })
        return root
    }

    private fun movetoposts(id: Int) {
        val intent = Intent(activity, Article_Post_show::class.java)
        intent.putExtra("articleId", id)
        startActivity(intent)
    }

    private fun settxtviewArticles(offset: Int, limit: Int) {
        println("offset: $offset ,limit: $limit")
        run {
            article1.text = ""
            article2.text = ""
            article3.text = ""
            article4.text = ""
            article5.text = ""
            article6.text = ""
            article7.text = ""
            article8.text = ""
            article9.text = ""
            article10.text = ""
            articleId1.text = ""
            articleId2.text = ""
            articleId3.text = ""
            articleId4.text = ""
            articleId5.text = ""
            articleId6.text = ""
            articleId7.text = ""
            articleId8.text = ""
            articleId9.text = ""
            articleId10.text = ""
        }//clear textviews

        val sharedPreferences =
            this.activity!!.getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", "NOT FOUND!!!")
        var obj = JSONObject()
        obj.put("offset", offset)
        obj.put("limit", limit)
        val que = Volley.newRequestQueue(this.activity)
        val jsonObjReq: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                constrants.GET_ARTICLES_URL,
                obj,
                Response.Listener {
                    var data = it.get("data") as JSONObject
                    var articles = data["articles"] as JSONArray
                    if (articles.toString() == "[]") {
                        headingArticles.text = "No more Articles found!!!"
                        article1.text = "can't find more Articles   :-("
                    } else {
                        for (i in 0 until articles.length()) {
                            when (i) {
                                0 -> {
                                    article1.append(articles.getJSONObject(0).getString("title"))
                                    articleId1.text =
                                        articles.getJSONObject(0).getInt("id").toString()
                                }
                                1 -> {
                                    article2.append(articles.getJSONObject(1).getString("title"))
                                    articleId2.text =
                                        articles.getJSONObject(1).getInt("id").toString()
                                }
                                2 -> {
                                    article3.append(articles.getJSONObject(2).getString("title"))
                                    articleId3.text =
                                        articles.getJSONObject(2).getInt("id").toString()
                                }
                                3 -> {
                                    article4.append(articles.getJSONObject(3).getString("title"))
                                    articleId4.text =
                                        articles.getJSONObject(3).getInt("id").toString()
                                }
                                4 -> {
                                    article5.append(articles.getJSONObject(4).getString("title"))
                                    articleId5.text =
                                        articles.getJSONObject(4).getInt("id").toString()
                                }
                                5 -> {
                                    article6.append(articles.getJSONObject(5).getString("title"))
                                    articleId6.text =
                                        articles.getJSONObject(5).getInt("id").toString()
                                }
                                6 -> {
                                    article7.append(articles.getJSONObject(6).getString("title"))
                                    articleId7.text =
                                        articles.getJSONObject(6).getInt("id").toString()
                                }
                                7 -> {
                                    article8.append(articles.getJSONObject(7).getString("title"))
                                    articleId8.text =
                                        articles.getJSONObject(7).getInt("id").toString()

                                }
                                8 -> {
                                    article9.append(articles.getJSONObject(8).getString("title"))
                                    articleId9.text =
                                        articles.getJSONObject(8).getInt("id").toString()

                                }
                                9 -> {
                                    article10.append(articles.getJSONObject(9).getString("title"))
                                    articleId10.text =
                                        articles.getJSONObject(9).getInt("id").toString()
                                }
                            }
                        }
                    }
                }
                ,
                Response.ErrorListener {
                    Toast.makeText(this.context, it.toString(), Toast.LENGTH_LONG).show()
                }) {
                /** Passing some request headers*  */
                @Throws(AuthFailureError::class)
                override fun getHeaders(): HashMap<String, String> {
                    val headers = HashMap<String, String>()
                    if (token != null) {
                        headers.put("AuthorizationToken", token)
                    }
                    headers.put("Content-Type", "application/json")
                    return headers
                }
            }
        que.add(jsonObjReq)
    }

    /*private fun gotoarticlesShow() {
        val intent = Intent(activity,Articles_show::class.java)
        startActivity(intent)
    }*/
}