package com.example.blogspot

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import com.example.blogspot.Utills.constrants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        btnregister.setOnClickListener {
            var obj = JSONObject()
            obj.put("name",edtname_register.text)
            obj.put("email",edtmail_register.text)
            obj.put("password",edtPass_register.text)
            //textView6.text=obj.toString()
            var que = Volley.newRequestQueue(this)
            var jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST,
                constrants.REGISTER_URL,
                obj,
                Response.Listener {
                    response ->
                    editor.putBoolean("Lstatus",false)
                    editor.commit()
                    var msg = response.get("message").toString()
                    val rstatus =response.get("status")
                    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
                    /*if(msg == "Email Allready Exits"){
                        var intent = Intent(this,MainActivity::class.java)
                        intent.putExtra("registred_mail",edtmail_register.text)
                        intent.putExtra("registred_pass",edtPass_register.text)
                        startActivity(intent)
                    }
                    else*/
                    if(msg == "Successfully created user" && rstatus==true)
                    {
                        var intent = Intent(this,MainActivity::class.java)
                        //intent.putExtra("registred_mail",edtmail_register.text)
                        //intent.putExtra("registred_pass",edtPass_register.text)
                        startActivity(intent)
                    }
                },
                Response.ErrorListener {
                    error ->
                    var msg = error.toString()
                    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
                }
            )
            que.add(jsonObjectRequest)
        }
        btnregistred.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}