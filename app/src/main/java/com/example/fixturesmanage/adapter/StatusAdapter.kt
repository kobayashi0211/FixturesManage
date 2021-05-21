package com.example.fixturesmanage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.fixturesmanage.R
import com.example.fixturesmanage.model.Status

class StatusAdapter(var mContext: Context, var resource: Int, var items: List<Status>) : ArrayAdapter<Status>(mContext, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        val view:View = layoutInflater.inflate(resource, null)

        val idTextView: TextView = view.findViewById(R.id.textViewId)
        val nameTextView: TextView = view.findViewById(R.id.textViewName)

        val mStatus: Status = items[position]
        idTextView.text = mStatus.id.toString()
        nameTextView.text = mStatus.name

        return view
    }

}