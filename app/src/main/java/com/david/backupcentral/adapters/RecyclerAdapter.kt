package com.david.backupcentral.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.backupcentral.R
import com.david.backupcentral.databinding.ImageItemDetailBinding
import com.david.backupcentral.models.ModelItem


class RecyclerAdapter(private val items: List<ModelItem>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var binding:ImageItemDetailBinding? = null

    //Cargamos los objetos de la vista en variables para el Bind
    inner class ViewHolder(binding: ImageItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.Iname
        val image = binding.IImage
        val desc=binding.IDesc
    }

    //AQUI Creamos el binding y hacemos el inflate de layout que forma parte de la vista de los items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        binding = ImageItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent ,false)
        return ViewHolder(binding!!)
    }
    //Cargamos la vista con los datos del constructor y el binding asociado en el ViewHolder
    override fun onBindViewHolder(viewHolder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.name.text = item.itemName

    }

    override fun getItemCount(): Int {
        return items.size
    }
}