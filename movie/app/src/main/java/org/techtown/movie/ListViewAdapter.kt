package org.techtown.movie

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

        val icon = view?.findViewById<ImageView>(R.id.image_title)
        val title = view?.findViewById<TextView>(R.id.text_title)
        val openingDate = view?.findViewById<TextView>(R.id.text_openingDate_title)
        val attendance = view?.findViewById<TextView>(R.id.text_attendance_title)

        val item = items[position]

        icon?.setImageDrawable(item.icon)
        title?.text = item.title
        openingDate?.text = item.openingDate
        attendance?.text = item.attendance

        return view!!
    }
}