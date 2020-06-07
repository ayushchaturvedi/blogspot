package com.example.blogspot

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.blogspot.Utills.constrants
import kotlinx.android.synthetic.main.activity_show_posts_comments.*
import org.json.JSONArray
import org.json.JSONObject

class ShowPostsComments : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_posts_comments)
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", "NOT FOUND!!!")
        val bundle = intent.extras
        var id = 0
        if (bundle != null) {
            id = bundle.getInt("postId")
        }
        val post_id=id
        btndeletePost.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Article !!!")
            builder.setMessage("Do you wont to Delete this Post,\n this will remove all the comments also.... ^_^ !!!")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        deletPost(post_id)
                    })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(this, "Operation Cancelled :-) !!!", Toast.LENGTH_SHORT)
                            .show()
                    })
            // Create the AlertDialog object and return it
            builder.show()
        }
        btnaddcomment.setOnClickListener {
            val intent = Intent(this, AddComment::class.java)
            intent.putExtra("postId", id)
            startActivity(intent)
        }
        println("Id: $id")
        run {
            postTitle.text = ""
            postDescription.text = ""
            postComments.text = ""
        }

        var obj = JSONObject()
        obj.put("post_id", id)
        val que = Volley.newRequestQueue(this)
        val jsonObjReq: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                constrants.VIEW_POST_URL,
                obj,
                Response.Listener {
                    //postTitle.text=it.toString()
                    var data = it.getJSONObject("data")
                    var post = data.getJSONArray("all_posts")


                    if (post.toString() == "[]") {
                        postTitle.text = "can't find Posts   :-("
                        headingPostsComments.text = "No more post found!!!"
                    } else {
                        postTitle.text = "Title :"
                        postTitle.append(post.getJSONObject(0).getString("title"))
                        postDescription.text = post.getJSONObject(0).getString("description")
                    }

                },
                Response.ErrorListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
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

        val jsonObjReq1: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                constrants.GET_COMMENTS_URL,
                obj,
                Response.Listener {
                    var data = it.get("data") as JSONObject
                    var cmnts = data["comments"] as JSONArray
                    postComments.text = "Comments: \n\n"
                    if (cmnts.toString() == "[]") {
                        postComments.append("No comments found <-_-> !!!")
                    } else {
                        for (i in 0 until cmnts.length()) {
                            postComments.append("Name : ")
                            postComments.append(cmnts.getJSONObject(i).getString("name"))
                            postComments.append("\nComment :")
                            postComments.append((cmnts.getJSONObject(i).getString("desceription")))
                            postComments.append("\n\n")
                        }
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
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
        que.add(jsonObjReq1)
    }

    private fun deletPost(id: Int) {
        println("Id: $id")
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", "NOT FOUND!!!")
        var obj = JSONObject()
        obj.put("id", id)
        val que = Volley.newRequestQueue(this)
        val jsonObjReq: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                constrants.DELETE_POST,
                obj,
                Response.Listener {
                    Toast.makeText(this, it.getString("message"), Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
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

