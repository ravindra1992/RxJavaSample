package com.example.moviewmvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviewmvvm.R
import com.example.moviewmvvm.ui.single_movie.SingleMovie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener{

            val intent= Intent(this, SingleMovie::class.java)
            intent.putExtra("id", 675327)
            this.startActivity(intent)
        }
    }
}