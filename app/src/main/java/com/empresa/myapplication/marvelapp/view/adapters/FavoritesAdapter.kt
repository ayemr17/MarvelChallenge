package com.empresa.myapplication.marvelapp.view.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp.model.local.FavoritesEntity
import com.empresa.myapplication.marvelapp.view.adapters.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_recyclerview_personajes.view.*

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class FavoritesAdapter(
    private val context: Context,
    private val personajesList: List<FavoritesEntity>,
    private val itemClickListener: OnPersonajeClickListener,
    private val itemLongClickListener: OnLongPersonajeClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return PersonajeViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_recyclerview_personajes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PersonajeViewHolder -> holder.bind(personajesList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return personajesList.size
    }

    inner class PersonajeViewHolder(itemView: View) : BaseViewHolder<FavoritesEntity>(itemView) {
        override fun bind(item: FavoritesEntity, position: Int) {

            var options: RequestOptions = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)

            Glide.with(context)
                .load(Uri.parse(item.imagen))
                .apply(options)
                .into(itemView.personaje_imageView_itemRecycler)

            itemView.nombrePJ_textView_itemRecycler.text = item.nombre
            if (item.descripcion.isNullOrEmpty()) {
                itemView.descripcionPJ_textView_itemRecycler.text = context.getString(R.string.sin_descrip)
            } else {
                itemView.descripcionPJ_textView_itemRecycler.text = item.descripcion
            }

            itemView.setOnClickListener {
                itemClickListener.onPersonajeClick(item)
                notifyDataSetChanged()
            }

            itemView.setOnLongClickListener {
                notifyDataSetChanged()
                return@setOnLongClickListener itemLongClickListener.onLongClickPersonajeListener(item, position)
            }
        }
    }

    interface OnPersonajeClickListener {
        fun onPersonajeClick(pj: FavoritesEntity)
    }

    interface OnLongPersonajeClickListener {
        fun onLongClickPersonajeListener(pj: FavoritesEntity, position: Int) : Boolean
    }

}