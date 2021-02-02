package com.empresa.myapplication.marvelapp._view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.Item
import com.empresa.myapplication.marvelapp._view.adapters.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_recyclerview_detalle.view.*

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class ListComicsAdapter(
    private val context: Context,
    private val comicsList: List<Item>,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ComicViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_recyclerview_detalle, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ComicViewHolder -> holder.bind(comicsList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

    inner class ComicViewHolder(itemView: View) : BaseViewHolder<Item>(itemView) {
        override fun bind(item: Item, position: Int) {
            itemView.nombreComic_textView_itemdetalle.text = item.name
        }
    }
}