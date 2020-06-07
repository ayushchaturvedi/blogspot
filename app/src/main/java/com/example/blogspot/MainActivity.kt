package com.example.blogspot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.blogspot.Utills.constrants
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("login_state",Context.MODE_PRIVATE)
        if(sharedPreferences.getBoolean("Lstatus",false)){
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener {
            val editor =  sharedPreferences.edit()

            var mail= edtmail.text.toString()
            var pass = edtPass.text.toString()
            var obj = JSONObject();
            obj.put("email",mail)
            obj.put("password",pass)
            var que = Volley.newRequestQueue(this)
            var req = JsonObjectRequest(
                Request.Method.POST,
                constrants.LOGIN_URL,
                obj,
                Response.Listener { response ->
                    Toast.makeText(
                        this,
                        response.getString("message").toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    if (response.get("message").toString() == "Login Successful!"){
                        var auth_token = response.get("data") as JSONObject
                        var token = auth_token.getString("auth_token")
                        editor.putBoolean("Lstatus",true)
                        editor.putString("auth_token",token)
                        editor.commit()
                        var intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    //textView2.text=it.getString("message")
                },
                Response.ErrorListener {
                    error ->
                    Toast.makeText(this,error.message,Toast.LENGTH_SHORT).show()
                })
            que.add(req)
        }
        btnsignup.setOnClickListener {
            var intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
        /*val bundle:Bundle?=intent.extras
        if(bundle!=null){
            val m=bundle?.get("registred_mail")
            val p=bundle?.get("registred_pass")
            if (m != null) {
                edtmail.text =Editable.Factory.getInstance().newEditable(m.toString())
            }
            if (p != null) {
                edtPass.text =Editable.Factory.getInstance().newEditable(p.toString())
            }
        }*/
    }
}