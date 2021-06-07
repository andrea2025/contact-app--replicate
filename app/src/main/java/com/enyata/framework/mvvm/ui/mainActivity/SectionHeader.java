package com.enyata.framework.mvvm.ui.mainActivity;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.List;

public class SectionHeader implements Section<ContactList> {
    List<ContactList> childList;
    String sectionText;

    public SectionHeader(List<ContactList> childList, String sectionText) {
        this.childList = childList;
        this.sectionText = sectionText;
    }
    @Override
    public List<ContactList> getChildItems() {
        return childList;
    }

    public String getSectionText() {
        return sectionText;
    }
}
