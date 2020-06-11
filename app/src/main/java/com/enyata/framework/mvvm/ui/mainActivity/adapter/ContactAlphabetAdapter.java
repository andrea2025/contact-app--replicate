package com.enyata.framework.mvvm.ui.mainActivity.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ui.mainActivity.OnAlphabetClick;
import com.enyata.framework.mvvm.ui.mainActivity.common.Common;

import java.util.List;

import text.drawable.mylibrary.TextDrawable;

public class ContactAlphabetAdapter extends RecyclerView.Adapter<ContactAlphabetAdapter.ViewHolder> {

    List<String> alphabetLists;

    OnAlphabetClick mOnAlphabetClick;

    public void setOnAlphabetClick(OnAlphabetClick onAlphabetClick) {
        mOnAlphabetClick = onAlphabetClick;
    }



    public ContactAlphabetAdapter() {
       alphabetLists = Common.genAlphabet();
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alphabet,parent,false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextDrawable textDrawable;
        final  int available_position = Common.alphabet_available.indexOf(alphabetLists.get(position));

        if (available_position != -1){
            textDrawable = TextDrawable.builder().buildRound(alphabetLists.get(position), Color.CYAN);
        }else {
            textDrawable = TextDrawable.builder().buildRound(alphabetLists.get(position),Color.RED);
        }
        holder.alphabet_avatar.setImageDrawable(textDrawable);
        holder.itemView.setOnClickListener(view -> {mOnAlphabetClick.OnAlphabetClickListener(alphabetLists.get(position),position);
        }
        );
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView alphabet_avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alphabet_avatar = (ImageView)itemView.findViewById(R.id.alphabet);
        }
    }
}
