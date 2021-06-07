package com.enyata.framework.mvvm.ui.mainActivity;

public class ContactList implements Comparable<ContactList>{
    private String contactName;
    private int ContactImage;
    private int viewType;
    private String contactNumber;
    private String contactEmail;
    private  String Id;

    public ContactList(){

    }




    public ContactList(String mId,int mContactImage,String mContactName,String mContactNumber, String mContactEmail){
        Id = mId;
        //viewType = mViewType;
        ContactImage = mContactImage;
        contactName = mContactName;
       contactNumber = mContactNumber;
       contactEmail = mContactEmail;


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
    public int getContactImage() {
        return ContactImage;
    }

    public void setContactImage(int contactImage) {
        ContactImage = contactImage;
    }
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public int compareTo(ContactList contactList) {
        return contactName.compareTo(contactList.contactName);
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
