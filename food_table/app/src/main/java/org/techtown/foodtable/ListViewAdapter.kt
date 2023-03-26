package org.techtown.foodtable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ListViewAdapter(private val items: MutableList<ListViewItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        }

        val day = view?.findViewById<TextView>(R.id.day)
        val date = view?.findViewById<TextView>(R.id.date)
        val morning = view?.findViewById<TextView>(R.id.morning)
        val launch = view?.findViewById<TextView>(R.id.launch)
        val diner = view?.findViewById<TextView>(R.id.diner)

        val item = items[position]

        day?.text = item.day
        date?.text = item.date
        morning?.text = item.morning
        launch?.text = item.launch
        diner?.text = item.diner

        return view!!
    }
}