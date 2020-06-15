package com.enyata.framework.mvvm.ui.mainActivity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ui.mainActivity.ContactList;
import com.enyata.framework.mvvm.ui.mainActivity.common.Common;
import com.enyata.framework.mvvm.ui.view_contact.ViewContactActivity;

import java.util.List;

import text.drawable.mylibrary.TextDrawable;
import text.drawable.mylibrary.util.ColorGenerator;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ContactList> mContactLists;


    public ContactAdapter(Context context, List<ContactList> contactLists) {
        this.context = context;
        mContactLists = contactLists;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
       if (viewType == Common.VIEWTYPE_GROUP)
       {
           ViewGroup group = (ViewGroup) layoutInflater.inflate(R.layout.section_header,viewGroup,false);
           GroupViewHolder groupViewHolder = new GroupViewHolder(group);
           return groupViewHolder;
       }else if (viewType == Common.VIEWTYPE_PERSON){
           ViewGroup group = (ViewGroup) layoutInflater.inflate(R.layout.contact_list,viewGroup,false);
           PersonViewHolder personViewHolder = new PersonViewHolder(group);
           return personViewHolder;
       }else {
           ViewGroup group = (ViewGroup) layoutInflater.inflate(R.layout.section_header,viewGroup,false);
           GroupViewHolder groupViewHolder = new GroupViewHolder(group);
           return groupViewHolder;
       }

    }

    @Override
    public int getItemViewType(int position) {
       return mContactLists.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupViewHolder){
            GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
            groupViewHolder.title.setText(mContactLists.get(position).getContactName());
        }else if (holder instanceof PersonViewHolder){
            PersonViewHolder personViewHolder = (PersonViewHolder)holder;
            personViewHolder.fullName.setText(mContactLists.get(position).getContactName());

            ColorGenerator mColor = ColorGenerator.MATERIAL;


            final String contactName = mContactLists.get(position).getContactName();
            TextDrawable drawable = TextDrawable.builder().buildRound(String.valueOf(contactName.charAt(0)),mColor.getRandomColor());
            personViewHolder.mImageView.setImageDrawable(drawable);


        personViewHolder.mRelativeLayout.setOnClickListener(view -> {
            //Toast.makeText(context, ""+ mContactLists.get(position).getContactName(), Toast.LENGTH_SHORT).show();




            Intent intent = new Intent(view.getContext(), ViewContactActivity.class);
            intent.putExtra("text",contactName);


            context.startActivity(intent);

        });
        }

    }

    @Override
    public int getItemCount() {
        return mContactLists.size();
    }


    private class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.section);


        }
    }

    private class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView fullName;
        ImageView mImageView;
        RelativeLayout mRelativeLayout;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.contactText);
            mImageView = itemView.findViewById(R.id.imageContact);
            mRelativeLayout = itemView.findViewById(R.id.parentLayout);
        }


    }


}
