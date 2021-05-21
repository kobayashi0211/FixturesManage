package com.example.fixturesmanage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.fixturesmanage.R
import com.example.fixturesmanage.model.Unit

class UnitAdapter(var mContext: Context, var resource: Int, var items: List<Unit>) : ArrayAdapter<Unit>(mContext, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        val view:View = layoutInflater.inflate(resource, null)

        val idTextView: TextView = view.findViewById(R.id.textViewId)
        val nameTextView: TextView = view.findViewById(R.id.textViewName)

        val mUnit: Unit = items[position]
        idTextView.text = mUnit.id.toString()
        nameTextView.text = mUnit.name

        return view
    }

}