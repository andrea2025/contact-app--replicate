package com.enyata.framework.mvvm.ui.mainActivity.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enyata.framework.mvvm.R;
import com.enyata.framework.mvvm.ui.mainActivity.ContactList;
import com.enyata.framework.mvvm.ui.view_contact.ViewContactActivity;

import java.util.ArrayList;
import java.util.List;

import text.drawable.mylibrary.TextDrawable;
import text.drawable.mylibrary.util.ColorGenerator;

import static com.enyata.framework.mvvm.ui.view_contact.ViewContactActivity.TAG;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ContactList> mContactLists;
    public static final int VIEW_TYPE_SECTION = 0;
    public static final int VIEW_TYPE_ITEM = 1;



    public ContactAdapter(Context context, List<ContactList> contactLists) {
        this.context = context;
        mContactLists = contactLists;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_SECTION) {
            View v = LayoutInflater.from(context).inflate(R.layout.contact_list, parent, false);
            return new ContactViewHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.section_header, parent, false);
            return new SectionViewHolder(v);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SectionViewHolder) {
           SectionViewHolder  groupViewHolder = (SectionViewHolder) holder;
           groupViewHolder.name.setText(mContactLists.get(position).getContactName());
        }else{
            ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
            ContactList contactList = mContactLists.get(position);
        Log.i(TAG, "name: " + contactList.getContactName());
        contactViewHolder.fullName.setText(contactList.getContactName());
        String contactName = contactList.getContactName();
        final String contactNumber = contactList.getContactNumber();
        final String cotactEmail = contactList.getContactEmail();
        final String id = contactList.getId();

            TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(0)
                .toUpperCase()
                .endConfig()
                .round();
        ColorGenerator mColor = ColorGenerator.MATERIAL;
        int color1 = mColor.getColor(contactName);
        TextDrawable textDrawable = builder.build(contactName.substring(0, 1), color1);
        contactViewHolder.mImageView.setImageDrawable(textDrawable);

            contactViewHolder.mRelativeLayout.setOnClickListener(view -> {
            //Toast.makeText(context, ""+ mContactLists.get(position).getContactName(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), ViewContactActivity.class);
            intent.putExtra("text", contactName);
            intent.putExtra("number", contactNumber);
            intent.putExtra("email", cotactEmail);
//            Log.e("id", "id" + id);
//            Log.e("name", "name" + contactName);
//            Log.e("number", "number" + contactNumber);
//            Log.e("email", "email" + cotactEmail);

            context.startActivity(intent);
        });

        }
    }

//
//    @Override
//    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
//        //Collections.sort(mContactLists, (o1, o2) -> o1.getContactName().compareTo(o2.getContactName()));
//        ContactList contactList = mContactLists.get(position);
//        Log.i(TAG, "name: " + contactList.getContactName());
//        holder.fullName.setText(contactList.getContactName());
//        String contactName = contactList.getContactName();
//        final String contactNumber = contactList.getContactNumber();
//        final String cotactEmail = contactList.getContactEmail();
//        final String id = contactList.getId();
//
//        TextDrawable.IBuilder builder = TextDrawable.builder()
//                .beginConfig()
//                .withBorder(0)
//                .toUpperCase()
//                .endConfig()
//                .round();
//        ColorGenerator mColor = ColorGenerator.MATERIAL;
//        int color1 = mColor.getColor(contactName);
//        // Log.i(TAG, "onBindViewHolder: " + color1);
//        Log.i(TAG, "onBindViewHolder: " + contactList);
//        Log.i(TAG, "onBindViewHolder: " + contactList.getContactNumber());
//        Log.i(TAG, "onBindViewHolder: " + contactList.getContactName());
//
//        TextDrawable textDrawable = builder.build(contactName.substring(0, 1), color1);
//        holder.mImageView.setImageDrawable(textDrawable);
//
//        holder.mRelativeLayout.setOnClickListener(view -> {
////            //Toast.makeText(context, ""+ mContactLists.get(position).getContactName(), Toast.LENGTH_SHORT).show();
////
////            Intent intent = new Intent(view.getContext(), ViewContactActivity.class);
////            intent.putExtra("text", contactName);
////            intent.putExtra("number", contactNumber);
////            intent.putExtra("email", cotactEmail);
////            Log.e("id", "id" + id);
////            Log.e("name", "name" + contactName);
////            Log.e("number", "number" + contactNumber);
////            Log.e("email", "email" + cotactEmail);
////
////            context.startActivity(intent);
////
////
////        });
////    }

//    @Override
//    public int getItemViewType(int position) {
//        return mContactLists.get(position).getViewType();
//    }

    @Override
    public int getItemCount() {
        return mContactLists.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView fullName;
        ImageView mImageView;
        LinearLayout mRelativeLayout;
        TextView mSection;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.contactText);
            mImageView = itemView.findViewById(R.id.imageContact);
            mRelativeLayout = itemView.findViewById(R.id.parentLayout);
            //  mSection =  itemView.findViewById(R.id.mSection);
        }

        public TextView getmSection() {
            return mSection;
        }

    }
    public class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public SectionViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.section);
        }
    }

    public void setItems(List<ContactList> datas) {
        mContactLists = new ArrayList<>(datas);
    }
}






