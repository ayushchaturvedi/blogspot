package com.example.blogspot

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.blogspot.Utills.constrants
import kotlinx.android.synthetic.main.activity_add_comment.*
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_show_posts_comments.*
import org.json.JSONObject

class AddComment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)
        val bundle=intent.extras
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", "NOT FOUND!!!")
        var id=0
        if(bundle!=null) {
            id=bundle.getInt("postId")
        }
        btnaddCmnt.setOnClickListener {
            if(edtcmnt.text.length>=3)
                addcomment(id,token)
            else
                Toast.makeText(this,"Please enter proper comments, \n (^_^)", Toast.LENGTH_LONG).show()

        }
    }

    private fun addcomment(id: Int, token: String?) {
        var obj = JSONObject()
        obj.put("post_id", id)
        obj.put("desc", edtcmnt.text)
        val que = Volley.newRequestQueue(this)
        val jsonObjReq: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                constrants.ADD_COMMENT_URL,
                obj,
                Response.Listener {
                    if (it.getBoolean("status")) {
                        Toast.makeText(this, it.getString("message"), Toast.LENGTH_LONG).show()
                        val intent = Intent(this, ShowPostsComments::class.java)
                        intent.putExtra("postId", id)
                        startActivity(intent)
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
    }
}

