package com.enyata.framework.mvvm.ui.mainActivity;

public class ContactList {


    private String contactName;
    private int viewType;

    public ContactList(){

    }

    public ContactList( String mContactName,int mViewType){

        contactName = mContactName;
        viewType = mViewType;

    }



    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
