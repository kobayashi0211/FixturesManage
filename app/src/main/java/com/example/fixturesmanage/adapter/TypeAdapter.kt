package com.example.fixturesmanage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.fixturesmanage.R
import com.example.fixturesmanage.model.Type

class TypeAdapter(var mContext: Context, var resource: Int, var items: List<Type>) : ArrayAdapter<Type>(mContext, resource, items) {

    override fun getItem(position: Int): Type? {
        return items[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        val view:View = layoutInflater.inflate(resource, null)

        val idTextView: TextView = view.findViewById(R.id.textViewId)
        val nameTextView: TextView = view.findViewById(R.id.textViewName)

        val mType: Type = items[position]
        idTextView.text = mType.id.toString()
        nameTextView.text = mType.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        val view:View = layoutInflater.inflate(resource, null)

        val idTextView: TextView = view.findViewById(R.id.textViewId)
        val nameTextView: TextView = view.findViewById(R.id.textViewName)

        val mType: Type = items[position]
        idTextView.text = mType.id.toString()
        nameTextView.text = mType.name

        return view
    }

}