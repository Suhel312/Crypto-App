package com.suhel.cryptoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.suhel.cryptoapp.databinding.RcviewItemBinding

class RvAdapter(val  context:Context,var data:ArrayList<Modal>): RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapter.ViewHolder {
        val view=RcviewItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RvAdapter.ViewHolder, position: Int) {
        setAnimation(holder.itemView)
        holder.binding.name.text= data[position].name
        holder.binding.symbol.text= data[position].symbol
        holder.binding.price.text= data[position].price
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun changeData(filterData: ArrayList<Modal>) {
        data=filterData
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RcviewItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    fun setAnimation(view:View){
        val anim=AlphaAnimation(0.0f,1.0f)
        anim.duration=1000
        view.startAnimation(anim)
    }
}

