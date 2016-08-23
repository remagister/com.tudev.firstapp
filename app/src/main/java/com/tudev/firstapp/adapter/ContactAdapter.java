package com.tudev.firstapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tudev.firstapp.EditPresenter;
import com.tudev.firstapp.R;
import com.tudev.firstapp.data.dao.Contact;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Саша on 27.07.2016.
 */
public class ContactAdapter extends StateAdapter<ContactAdapterState> {

    private static class ViewHolder{

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.nameText) TextView nameLabel;
        @BindView(R.id.descrText) TextView infoLabel;
        @BindView(R.id.contactThumb) ImageView imageView;
        @BindView(R.id.checkableView_selection) CheckBox checkBox;
    }

    private static class WeakViewHolder implements IStateChangedListener<ContactAdapterState>{

        private WeakReference<ViewHolder> viewHolderWeakReference;
        private ViewHolder strongReference;

        WeakViewHolder(ViewHolder holder) {
            viewHolderWeakReference = new WeakReference<>(holder);
            EventBus.getDefault().register(this);
        }

        void revive(ViewHolder holder){
            viewHolderWeakReference = new WeakReference<ViewHolder>(holder);
            if(!EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().register(this);
            }
        }

        ViewHolder get(){
            return viewHolderWeakReference.get();
        }

        public ViewHolder open(){
            if(strongReference != null) return strongReference;
            strongReference = get();
            return strongReference;
        }

        public void close(){
            strongReference = null;
        }

        @Override
        @Subscribe
        public void onStateChanged(StateChangedEvent<ContactAdapterState> event) {
            ViewHolder holder = get();
            if(holder != null){
                switchState(holder.checkBox, event.getState());
            }
            else{
                EventBus.getDefault().unregister(this);
            }
        }
    }

    // ========== internal members ==========

    private List<Contact.ContactSimple> internalContacts;

    public ContactAdapter(LayoutInflater infl, List<Contact.ContactSimple> contacts){
        this(infl, contacts, ContactAdapterState.NORMAL);
    }

    public ContactAdapter(LayoutInflater inflater, List<Contact.ContactSimple> contacts, ContactAdapterState state){
        super(state, inflater);
        internalContacts = new ArrayList<>();
        internalContacts.addAll(contacts);
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

    private static void switchState(CheckBox box, ContactAdapterState state){
        switch (state){
            case NORMAL: {
                box.setVisibility(View.GONE);
                break;
            }
            case SELECTION:{
                box.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void fillHolder(Contact.ContactSimple contact, ViewHolder holder, ContactAdapterState state){
        holder.nameLabel.setText(contact.getName());
        holder.infoLabel.setText(contact.getPhone());
        if (!contact.getImageThumb().equals(Contact.EMPTY)) {
            createThumb(getInternalInflater().getContext(), contact.getImageThumb(), holder.imageView);
        }
        switchState(holder.checkBox, state);
    }

    @Override
    protected final View getViewState(int i, View view, ViewGroup group, ContactAdapterState state) {
        WeakViewHolder weakViewHolder;
        if(view == null){
            view = getInternalInflater().inflate(R.layout.contact_view, group, false);  // do not attach to root
            weakViewHolder = new WeakViewHolder(new ViewHolder(view));
            view.setTag(weakViewHolder);
            view.setSelected(true);
        }
        else {
            weakViewHolder = (WeakViewHolder) view.getTag();
        }
        Contact.ContactSimple currentContact = getItem(i);
        ViewHolder holder = weakViewHolder.get();
        if(holder != null) {
            fillHolder(currentContact, holder,state);
        }
        else {
            holder = new ViewHolder(view);
            fillHolder(currentContact, holder,state);
            weakViewHolder.revive(holder);
        }
        return view;
    }

}
