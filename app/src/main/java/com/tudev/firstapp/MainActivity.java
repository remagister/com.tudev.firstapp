package com.tudev.firstapp;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ListView;

import com.tudev.firstapp.adapter.ContactAdapter;
import com.tudev.firstapp.adapter.ContactAdapterState;
import com.tudev.firstapp.adapter.StateAdapter;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.view.ViewBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ViewBase implements IMainView {

    private AdapterView.OnItemLongClickListener longClickListener = new ItemLongClickListener();
    private AdapterView.OnItemClickListener itemClickListener = new ItemClickListener();
    private StateAdapter<ContactAdapterState> adapter;
    private IMainPresenter presenter;
    @BindView(R.id.listViewOne) ListView listView;
    @BindView(R.id.listActionButton) Button actionButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter.onCreate(getApplicationContext());
        listView.setOnItemClickListener(itemClickListener);

        actionButton.setTag(AddButtonIntent.ADD);

        listView.setOnItemLongClickListener(longClickListener);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buttonClicked((AddButtonIntent) actionButton.getTag());
            }
        });
    }

    void setButtonState(ContactAdapterState state){
        switch (state){
            case SELECTION:{
                actionButton.setText(MainActivity.this.getString(R.string.remove_button));
                actionButton.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                        android.R.color.holo_red_dark));
                actionButton.setTag(AddButtonIntent.REMOVE);
                break;
            }
            case NORMAL:{
                actionButton.setText(MainActivity.this.getString(R.string.add_button));
                actionButton.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                        android.R.color.holo_green_dark));
                actionButton.setTag(AddButtonIntent.ADD);
            }

        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            presenter.itemClicked((Contact.ContactSimple) adapterView.getItemAtPosition(i));
        }
    }

    private class ItemLongClickListener implements AdapterView.OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(adapter.getState() == ContactAdapterState.SELECTION){
                return false;
            }
            listView.setOnItemClickListener(null);
            ((Checkable) view).setChecked(true);
            adapter.stateChanged(ContactAdapterState.SELECTION);
            setButtonState(ContactAdapterState.SELECTION);
            adapter.notifyDataSetInvalidated();
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // user re-enters activity here
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setContactsList(List<Contact.ContactSimple> contacts) {
        adapter = new ContactAdapter(getLayoutInflater(), contacts);
        listView.setAdapter(adapter);
    }

    @Override
    public void notifyDataChanged() {
        setButtonState(ContactAdapterState.NORMAL);
        adapter.stateChanged(ContactAdapterState.NORMAL);
        adapter.notifyDataSetInvalidated();

    }

    @Override
    public List<Contact.ContactSimple> getRemoveList() {
        SparseBooleanArray array = listView.getCheckedItemPositions();
        List<Contact.ContactSimple> removeList = new ArrayList<>();
        for (int i = 0; i < array.size(); ++i) {
            if(array.get(i)){
                removeList.add((Contact.ContactSimple) listView.getItemAtPosition(i));
            }
        }
        return removeList;
    }

}
