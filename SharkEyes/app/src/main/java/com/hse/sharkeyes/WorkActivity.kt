package com.hse.sharkeyes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_work.*
import androidx.recyclerview.widget.DividerItemDecoration



class WorkActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = WorkAdapter(this, layoutInflater)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}

class WorkViewHolder(val context: Context, view: View): RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener(View.OnClickListener { context.startActivity(Intent(context, DiagnosticIntroActivity::class.java)) })
    }
}

class WorkAdapter(val context: Context, val layoutInflater: LayoutInflater): RecyclerView.Adapter<WorkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        val view = layoutInflater.inflate(R.layout.layout_work_item, parent, false)
        return WorkViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
    }
}