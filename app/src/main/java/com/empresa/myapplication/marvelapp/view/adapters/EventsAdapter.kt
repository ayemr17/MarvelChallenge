package com.empresa.myapplication.marvelapp.view.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp.model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp.view.adapters.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_recyclerview_eventos.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class EventsAdapter(
    private val context: Context,
    private val eventoList: List<ResultEventos>,
    private val itemClickListener: OnEventoClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return EventosViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_recyclerview_eventos, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is EventosViewHolder -> holder.bind(eventoList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return eventoList.size
    }

    inner class EventosViewHolder(itemView: View) : BaseViewHolder<ResultEventos>(itemView) {
        override fun bind(item: ResultEventos, position: Int) {

            itemView.listComics_recyclerView_eventos.layoutManager = LinearLayoutManager(context)
            itemView.listComics_recyclerView_eventos.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )

            var options: RequestOptions = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)

            Glide.with(context)
                .load(Uri.parse(item.thumbnail?.path + "." + item.thumbnail?.extension))
                .apply(options)
                .into(itemView.imagenEvento_imageView_eventos)
            itemView.nombreEvento_textView_eventos.text = item.title

            // espacio donde trabajamos con las fechas otorgadas por la api
            val dateStart : Date
            val dateEnd : Date
            var fechaInicio  = ""
            var fechaFin  = ""
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                if (item.start.toString() != "null") {
                    dateStart = format.parse(item.start.toString())
                    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
                    fechaInicio = simpleDateFormat.format(dateStart)
                } else {
                    fechaInicio = context.getString(R.string.fecha_inicio)
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            try {
                if (item.end.toString() != "null") {
                    dateEnd = format.parse(item.start.toString())
                    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
                    fechaFin = simpleDateFormat.format(dateEnd)
                } else {
                    fechaFin = context.getString(R.string.fecha_fin)
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            // fin del espacio de fechas

            itemView.fechaEvento_textView_eventos.text = fechaInicio + "\n" + fechaFin

            itemView.setOnClickListener {
                itemClickListener.onEventoClick(item, it)
            }
        }
    }

    interface OnEventoClickListener {
        fun onEventoClick(evento: ResultEventos, view: View)
    }
}