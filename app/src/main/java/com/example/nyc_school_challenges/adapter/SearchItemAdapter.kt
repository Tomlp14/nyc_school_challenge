package com.example.nyc_school_challenges.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nyc_school_challenges.ScoreFragment
import com.example.nyc_school_challenges.databinding.LayoutSearchItemBinding
import com.example.nyc_school_challenges.model.SchoolModel
import com.example.nyc_school_challenges.viewmodel.SchoolViewModel

class SearchItemAdapter(val activity: AppCompatActivity, val viewModel: SchoolViewModel,  var schools : List<SchoolModel>) : RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = LayoutSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return schools.size
    }

    fun changeData(newData : List<SchoolModel>){
        schools = newData
        notifyDataSetChanged()
    }

    inner class SearchItemViewHolder(val binding: LayoutSearchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.tvSchoolName.text = schools[position].schoolName
            binding.tvSchoolAddress.text = schools[position].addressString
            binding.clTop.setOnClickListener{
                //will launch a dialog fragment by our context
                viewModel.select(schools[position])
                ScoreFragment.newInstance().show(activity.supportFragmentManager, "SchoolDetailDialogFragment")


            }
        }
    }
}