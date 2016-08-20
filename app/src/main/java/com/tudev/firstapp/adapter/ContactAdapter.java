package com.tudev.firstapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tudev.firstapp.EditPresenter;
import com.tudev.firstapp.R;
import com.tudev.firstapp.adapter.ContactAdapterState;
import com.tudev.firstapp.adapter.StateAdapter;
import com.tudev.firstapp.data.dao.Contact;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Саша on 27.07.2016.
 */
public class ContactAdapter extends StateAdapter<ContactAdapterState> {

    private List<Contact.ContactSimple> internalContacts;

    public ContactAdapter(LayoutInflater infl, List<Contact.ContactSimple> contacts){
        this(infl, contacts, ContactAdapterState.NORMAL);
    }

    public ContactAdapter(LayoutInflater inflater, List<Contact.ContactSimple> contacts, ContactAdapterState state){
        super(state, inflater);
        internalContacts = new ArrayList<>();
        internalContacts.addAll(contacts);
    }

    private static class ViewHolder{
        TextView nameLabel;
        TextView infoLabel;
        ImageView imageView;
        CheckBox checkBox;
    }

    private ViewHolder createHolder(View view){
        ViewHolder ret = new ViewHolder();
        ret.nameLabel = (TextView) view.findViewById(R.id.nameText);
        ret.infoLabel = (TextView) view.findViewById(R.id.descrText);
        ret.imageView = (ImageView) view.findViewById(R.id.contactThumb);
        ret.checkBox = (CheckBox) view.findViewById(R.id.checkableView_selection);
        return ret;
    }

    @Override
    public int getCount() {
        return internalContacts.size();
    }

    @Override
    public Contact.ContactSimple getItem(int i) {
        return internalContacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static void createThumb(Context context, String name, ImageView view){
        Uri filename = Uri.withAppendedPath(EditPresenter.getThumbsUri(context), name);
        Picasso.with(context).load(filename).into(view);
    }

    @Override
    protected final View getViewState(int i, View view, ViewGroup group, ContactAdapterState state) {
        ViewHolder holder;
        if(view == null){
            view = getInternalInflater().inflate(R.layout.contact_view, group, false);  // do not attach to root
            holder = createHolder(view);
            view.setTag(holder);
            view.setSelected(true);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        Contact.ContactSimple currentContact = getItem(i);
        holder.nameLabel.setText(currentContact.getName());
        holder.infoLabel.setText(currentContact.getPhone());
        if(!currentContact.getImageThumb().equals(Contact.EMPTY)) {
            createThumb(getInternalInflater().getContext(), currentContact.getImageThumb(), holder.imageView);
        }
        switch (state){
            case NORMAL: {
                holder.checkBox.setVisibility(View.GONE);
                break;
            }
            case SELECTION:{
                holder.checkBox.setVisibility(View.VISIBLE);
                break;
            }
        }
        // TODO: 09.08.16 SET CONTACT THUMBNAIL
        return view;
    }

}
