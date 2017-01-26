package com.optimussoftware.boohos.invitations.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.invitations.GrowHackingActivity;
import com.optimussoftware.boohos.invitations.GrowHackingModel;
import com.optimussoftware.boohos.invitations.adapters.GrowHackignViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leonardojpr on 12/6/16.
 */

public class ListContactGrowHackingFragment extends Fragment {

    private static final String TAG = ListContactGrowHackingFragment.class.getSimpleName();
    private static final String INDEX = "index";
    private int index = 0;

    private RecyclerView recyclerView;

    public static ListContactGrowHackingFragment newInstance(int index) {

        // Instantiate a new fragment
        ListContactGrowHackingFragment fragment = new ListContactGrowHackingFragment();

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.list_contact_grow_hacking, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.index = getArguments().getInt(INDEX);
        System.out.print("index " + index);
        switch (index) {
            case GrowHackingActivity.CONNECT_FRIEND:
                setLoadData(index);
                break;

            case GrowHackingActivity.INVITE_APP:
                setLoadData(index);
                break;
        }

    }


    private void setLoadData(int from) {
        ArrayList<GrowHackingModel> hackingModels = getContactList();
        initRecyclerView(new GrowHackignViewAdapter(hackingModels, from));
    }

    public void initRecyclerView(GrowHackignViewAdapter adapter) {

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);
    }

    public ArrayList<GrowHackingModel> getContactList() {

        HashMap<String, GrowHackingModel> contactListHm = new HashMap<>();
        ArrayList<GrowHackingModel> contactList = new ArrayList<>();

        String phoneNumber = null;
        String email = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneURI_CONTENT = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output = new StringBuffer();

        ContentResolver contentResolver = getActivity().getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    Cursor phoneCursor = contentResolver.query(PhoneURI_CONTENT, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));

                    }
                    phoneCursor.close();

                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                    }
                    emailCursor.close();
                }
                Log.d(TAG, name + " " + phoneNumber + " " + email);
                contactListHm.put(contact_id, new GrowHackingModel(false, null, email, email));
            }

        }

        for (Map.Entry<String, GrowHackingModel> uno : contactListHm.entrySet()) {
            contactList.add(uno.getValue());
        }

        return contactList;
    }


}