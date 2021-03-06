package com.enyata.framework.mvvm.ui.mainActivity.common;

import com.enyata.framework.mvvm.ui.mainActivity.ContactList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Common {
    public static int VIEWTYPE_GROUP = 1;
    public static final int VIEWTYPE_PERSON = 2;


    public static List<String> alphabet_available = new ArrayList<>();
    public static int RESULT_CODE = 1000;

    //sort person list name by alphabet




    public static ArrayList<ContactList> sortList(ArrayList<ContactList> people){

        Collections.sort(people, new Comparator<ContactList>() {
            @Override
            public int compare(ContactList contactList, ContactList t1) {
                return contactList.getContactName().compareTo(t1.getContactName());
            }
        });
        return people;
    }




    //after sorting, add alphabet to the list

//    public  static ArrayList<ContactList> addAlphabet(ArrayList<ContactList> list){
//        int i = 0;
//        ArrayList<ContactList> customList = new ArrayList<>();
//        ContactList firstPosition = new ContactList(name.getText().toString(), number.getText().toString(), email.getText().toString());
//        firstPosition.setContactName(String.valueOf(list.get(0).getContactName().charAt(0)));
//        firstPosition.setViewType(VIEWTYPE_GROUP);
//        //customList.add(firstPosition);
//       // alphabet_available.add(String.valueOf(list.get(0).getContactName().charAt(0)));//add first character to group header
//
//        customList.add(firstPosition);
//
//        for (i = 0;i < list.size() - 1;i++){
//            ContactList contactList = new ContactList(name.getText().toString(), number.getText().toString(), email.getText().toString());
//            char name1 = list.get(i).getContactName().charAt(0);//get first character in name
//            char name2 = list.get(i + 1).getContactName().charAt(0);
//
//            if (name1 == name2){
//                list.get(i).setViewType(VIEWTYPE_PERSON);
//                customList.add(list.get(i));
//            }else {
//                list.get(i).setViewType(VIEWTYPE_PERSON);
//                customList.add(list.get(i));
//                contactList.setContactName(String.valueOf(name2));
//                contactList.setViewType(VIEWTYPE_GROUP);
//                //alphabet_available.add(String.valueOf(name2));
//                customList.add(contactList);
//            }
//        }
//        list.get(i).setViewType(VIEWTYPE_PERSON);
//        customList.add(list.get(i));
//        return customList;
//    }



    //return position of string in the list

    public static int findPositionWithName(String name,ArrayList<ContactList>list){
        for (int i=0;i<list.size();i++){
            if (list.get(i).getContactName().equals(name))
                return i;
        }
        return -1; //if not found
    }

    //generate alphabet list
    public static ArrayList<String> genAlphabet(){
        ArrayList<String> result = new ArrayList<>();
        for (int i = 65;i<90;i++) //65 in ASCII = A n Z = 90
        {
            char character = (char) i;
            result.add(String.valueOf(character));
        }
        return result;
    }


}
