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
import com.empresa.myapplication.marvelapp.databinding.ItemRecyclerviewPersonajesBinding
import com.empresa.myapplication.marvelapp.model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.view.adapters.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_recyclerview_personajes.view.*

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class CharactersAdapter(
    private val context: Context,
    private val personajesList: List<Result>,
    private val itemClickListener: OnPersonajeClickListener,
    private val itemLongClickListener: OnLongPersonajeClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        /*val binding = ItemRecyclerviewPersonajesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonajeViewHolder(binding.root)*/
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

    inner class PersonajeViewHolder(view: View) : BaseViewHolder<Result>(view) {

        private val binding = ItemRecyclerviewPersonajesBinding.bind(view)

        override fun bind(item: Result, position: Int) {

            var options: RequestOptions = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)

            Glide.with(context)
                .load(Uri.parse(item.thumbnail?.path + "." + item.thumbnail?.extension))
                .apply(options)
                .into(itemView.personaje_imageView_itemRecycler)

            with(binding) {
                itemView.nombrePJ_textView_itemRecycler.text = item.name
                if (item.description.isNullOrEmpty()) {
                    itemView.descripcionPJ_textView_itemRecycler.text = context.getString(R.string.sin_descrip)
                } else {
                    itemView.descripcionPJ_textView_itemRecycler.text = item.description
                }

                itemView.setOnClickListener {
                    itemClickListener.onPersonajeClick(item)
                }

                itemView.setOnLongClickListener {
                    return@setOnLongClickListener itemLongClickListener.onLongClickPersonajeListener(item)
                }
            }
        }
    }

    interface OnPersonajeClickListener {
        fun onPersonajeClick(pj: Result)
    }

    interface OnLongPersonajeClickListener {
        fun onLongClickPersonajeListener(pj: Result) : Boolean
    }
}