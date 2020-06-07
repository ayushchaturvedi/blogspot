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
import kotlinx.android.synthetic.main.activity_add_post.*
import org.json.JSONObject

class Add_Edit_articles : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__edit_articles)
        val sharedPreferences = getSharedPreferences("login_state", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", "NOT FOUND!!!")
        val bundle=intent.extras
        var id: Int = 0
        if (bundle != null) {
            id = bundle.getInt("articleId")
            println("Article is: $id")
        }
        btnadd_post.setOnClickListener {
            //Toast.makeText(this,id.toString(),Toast.LENGTH_SHORT).show()
            if(edtArticleTitle.text.length >=3){
                addArticle(id,token)
            }
            else
                Toast.makeText(this,"please fill title correctly (*_*) !", Toast.LENGTH_LONG).show()
        }
    }

    private fun addArticle(id: Int, token: String?) {
        var obj = JSONObject()
        obj.put("id", id)
        obj.put("title", edtArticleTitle.text)
        val que = Volley.newRequestQueue(this)
        val jsonObjReq: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                constrants.ADD_EDIT_ARTICLE,
                obj,
                Response.Listener {
                    Toast.makeText(this,it.getString("message"), Toast.LENGTH_LONG).show()

                    val intent=Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                }
                ,
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
}