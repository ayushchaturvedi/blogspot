package com.example.blogspot

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.blogspot.Utills.constrants
import kotlinx.android.synthetic.main.activity_articles_post_show.*
import kotlinx.android.synthetic.main.activity_articles_post_show.btnEditArticle
import kotlinx.android.synthetic.main.activity_articles_post_show.btnnext
import kotlinx.android.synthetic.main.activity_articles_post_show.btnprev
import kotlinx.android.synthetic.main.activity_articles_post_show.headingPostsArticles
import kotlinx.android.synthetic.main.activity_articles_post_show.postid1
import kotlinx.android.synthetic.main.activity_articles_post_show.postid10
import kotlinx.android.synthetic.main.activity_articles_post_show.postid2
import kotlinx.android.synthetic.main.activity_articles_post_show.postid3
import kotlinx.android.synthetic.main.activity_articles_post_show.postid4
import kotlinx.android.synthetic.main.activity_articles_post_show.postid5
import kotlinx.android.synthetic.main.activity_articles_post_show.postid6
import kotlinx.android.synthetic.main.activity_articles_post_show.postid7
import kotlinx.android.synthetic.main.activity_articles_post_show.postid8
import kotlinx.android.synthetic.main.activity_articles_post_show.postid9
import kotlinx.android.synthetic.main.activity_show_posts_comments.*
import org.json.JSONArray
import org.json.JSONObject


class Article_Post_show : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_post_show)
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", "NOT FOUND!!!")
        val bundle: Bundle? = intent.extras
        var id: Int = 0
        if (bundle != null) {
            id = bundle.getInt("articleId")
        }
        val ArticleId =id
        var offset = 0
        var limit = 10
        showposts(offset, limit, id, token)
        btnprev.setOnClickListener {
            if (offset >= 10) {
                offset -= 10
                limit -= 10
                headingPostsArticles.text = "Posts"
                showposts(offset, limit, id, token)
            }
        }
        btnAddPost.setOnClickListener {
            val intent = Intent(this,AddPost::class.java)
            intent.putExtra("articleId",id)
            startActivity(intent)
        }
        btnnext.setOnClickListener {
            if (offset >= 0 && headingPostsArticles.text == "Posts") {
                offset += 10
                limit += 10
                showposts(offset, limit, id, token)
            }
        }

        btnEditArticle.setOnClickListener {
            var intent = Intent(this, Add_Edit_articles::class.java)
            intent.putExtra("articleId", id)
            startActivity(intent)
        }
        btndeleteArticle.setOnClickListener { it ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Article !!!")
            builder.setMessage("Do you wont to Delete this article,\n this will remove all the posts also.... ^_^ !!!")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        deleteArticle(ArticleId)
                    })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(this, "Operation Cancelled :-) !!!", Toast.LENGTH_SHORT)
                            .show()
                    })
            // Create the AlertDialog object and return it
            builder.show()
        }
        run{
            post1.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid1.text.toString().toInt())
                startActivity(intent)
            }
            post2.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid2.text.toString().toInt())
                startActivity(intent)
            }
            post3.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid3.text.toString().toInt())
                startActivity(intent)
            }
            post4.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid4.text.toString().toInt())
                startActivity(intent)
            }
            post5.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid5.text.toString().toInt())
                startActivity(intent)

            }
            post6.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid6.text.toString().toInt())
                startActivity(intent)

            }
            post7.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid7.text.toString().toInt())
                startActivity(intent)

            }
            post8.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid8.text.toString().toInt())
                startActivity(intent)

            }
            post9.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid9.text.toString().toInt())
                startActivity(intent)
            }
            post10.setOnClickListener {
                val intent=Intent(this,ShowPostsComments::class.java)
                intent.putExtra("postId",postid10.text.toString().toInt())
                startActivity(intent)
            }
        }
    }

    private fun deleteArticle(id: Int) {
        println("Id: $id")
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", "NOT FOUND!!!")
        var obj = JSONObject()
        obj.put("id", id)
        val que = Volley.newRequestQueue(this)
        val jsonObjReq: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                constrants.DELETE_ARTICLE,
                obj,
                Response.Listener {
                    Toast.makeText(this,it.getString("message"), Toast.LENGTH_LONG).show()
                    val intent=Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener {
                    Toast.makeText(this,it.toString(), Toast.LENGTH_LONG).show()
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


    private fun showposts(offset: Int, limit: Int, id: Int, token: String?) {
        println("offset: $offset , limit: $limit , id: $id")
        run {
            post1.text = ""
            post2.text = ""
            post3.text = ""
            post4.text = ""
            post5.text = ""
            post6.text = ""
            post7.text = ""
            post8.text = ""
            post9.text = ""
            post10.text = ""
            postid1.text=""
            postid2.text=""
            postid3.text=""
            postid4.text=""
            postid5.text=""
            postid6.text=""
            postid7.text=""
            postid8.text=""
            postid9.text=""
            postid10.text=""
        }//clear posts
        var obj = JSONObject()
        obj.put("offset", offset)
        obj.put("limit", limit)
        obj.put("article_id", id)
        val que = Volley.newRequestQueue(this)
        val jsonObjReq: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                constrants.GET_ARTICLES_POSTS,
                obj,
                Response.Listener {
                    //Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                    var data = it.get("data") as JSONObject
                    var posts = data["posts"] as JSONArray
                    if (posts.toString() == "[]") {
                        post1.text = "can't find more Posts   :-("
                        headingPostsArticles.text = "No more post found!!!"
                    } else {
                        for (i in 0 until posts.length()) {
                            when (i) {
                                0 -> {
                                    post1.text = "Title :"
                                    post1.append(posts.getJSONObject(0).getString("title"))
                                    postid1.text=posts.getJSONObject(0).getInt("id").toString()
                                }
                                1 -> {
                                    post2.text = "Title :"
                                    post2.append(posts.getJSONObject(1).getString("title"))
                                    postid2.text=posts.getJSONObject(1).getInt("id").toString()
                                }
                                2 -> {
                                    post3.text = "Title :"
                                    post3.append(posts.getJSONObject(2).getString("title"))
                                    postid3.text=posts.getJSONObject(2).getInt("id").toString()
                                }
                                3 -> {
                                    post4.text = "Title :"
                                    post4.append(posts.getJSONObject(3).getString("title"))
                                    postid4.text=posts.getJSONObject(3).getInt("id").toString()
                                }
                                4 -> {
                                    post5.text = "Title :"
                                    post5.append(posts.getJSONObject(4).getString("title"))
                                    postid5.text=posts.getJSONObject(4).getInt("id").toString()
                                }
                                5 -> {
                                    post6.text = "Title :"
                                    post6.append(posts.getJSONObject(5).getString("title"))
                                    postid6.text=posts.getJSONObject(5).getInt("id").toString()
                                }
                                6 -> {
                                    post7.text = "Title :"
                                    post7.append(posts.getJSONObject(6).getString("title"))
                                    postid7.text=posts.getJSONObject(6).getInt("id").toString()
                                }
                                7 -> {
                                    post8.text = "Title :"
                                    post8.append(posts.getJSONObject(7).getString("title"))
                                    postid8.text=posts.getJSONObject(7).getInt("id").toString()
                                }
                                8 -> {
                                    post9.text = "Title :"
                                    post9.append(posts.getJSONObject(8).getString("title"))
                                    postid9.text=posts.getJSONObject(8).getInt("id").toString()
                                }
                                9 -> {
                                    post10.text = "Title :"
                                    post10.append(posts.getJSONObject(9).getString("title"))
                                    postid10.text=posts.getJSONObject(9).getInt("id").toString()
                                }
                            }
                        }
                    }
                }
                ,
                Response.ErrorListener {
                    //txtgetarticles_show_Post.text=it.toString()
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
}