package com.example.mydreams.main.dreams_list;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mydreams.databinding.ItemLayoutBinding;
import com.example.mydreams.model.Dreams;

public class DreamsListViewHolder extends RecyclerView.ViewHolder {

    ItemLayoutBinding binding;

    public DreamsListViewHolder(ItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        //itemView.setOnClickListener(this);
        //itemView.setOnLongClickListener(this);
    }

    void bind(Dreams dream, int color) {
        binding.setDreams(dream);
        binding.setColor(color);
        binding.executePendingBindings();
    }
//
//    @Override
//    public void onClick(View v) {
//
//    }

//    @Override
//    public boolean onLongClick(View v) {
//        return false;
//    }
}
