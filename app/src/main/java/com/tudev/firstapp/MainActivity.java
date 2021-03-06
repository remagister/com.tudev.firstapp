package com.tudev.firstapp;

import android.app.AlarmManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.tudev.firstapp.adapter.ContactAdapter;
import com.tudev.firstapp.adapter.ContactAdapterState;
import com.tudev.firstapp.adapter.StateAdapter;
import com.tudev.firstapp.adapter.StateChangedEvent;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.view.ViewBase;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ViewBase<IMainPresenter> implements IMainView {


    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            getPresenter().itemClicked((Contact.ContactSimple) adapterView.getItemAtPosition(i));
            listView.setItemChecked(i, false);
        }
    }

    private class ItemLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapter.getState() == ContactAdapterState.SELECTION) {
                return false;
            }
            getPresenter().onItemLongClick();
            listView.setItemChecked(i, true);
            return true;
        }
    }

    // ========== INTERNAL MEMBERS ==========

    private AdapterView.OnItemLongClickListener longClickListener = new ItemLongClickListener();
    private AdapterView.OnItemClickListener itemClickListener = new ItemClickListener();
    private StateAdapter<ContactAdapterState> adapter;
    @BindView(R.id.listViewOne)
    ListView listView;
    @BindView(R.id.listActionButton)
    Button actionButton;

    @Override
    public IMainPresenter onPresenterCreate() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            getPresenter().onLoadState(savedInstanceState);
        }
        initializePresenter();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().buttonClicked((AddButtonIntent) actionButton.getTag());
            }
        });
    }

    void setToolbar(){
        Toolbar tb = (Toolbar) findViewById(R.id.main_layout_toolbar);
        setSupportActionBar(tb);
        tb.setTitle(R.string.app_name);
    }

    public void clearChecks(){
        for (int i = 0; i < adapter.getCount(); ++i) {
            listView.setItemChecked(i, false);
        }
    }

    @Override
    public void onBackPressed() {
        if (adapter.getState() == ContactAdapterState.SELECTION) {
            // deselect all
            clearChecks();
            getPresenter().onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void initState(ContactAdapterState state) {
        setSelectionMode(state);
        if (state == ContactAdapterState.NORMAL) {
            listView.setOnItemClickListener(itemClickListener);
            listView.setOnItemLongClickListener(longClickListener);
        } else {
            listView.setOnItemClickListener(null);
            listView.setOnItemLongClickListener(null);
        }
    }


    void setButtonState(ContactAdapterState state) {
        switch (state) {
            case SELECTION: {
                actionButton.setText(MainActivity.this.getString(R.string.remove_button));
                actionButton.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                        android.R.color.holo_red_dark));
                actionButton.setTag(AddButtonIntent.REMOVE);
                break;
            }
            case NORMAL: {
                actionButton.setText(MainActivity.this.getString(R.string.add_button));
                actionButton.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                        android.R.color.holo_green_dark));
                actionButton.setTag(AddButtonIntent.ADD);
            }

        }
    }

    private void setSelectionMode(ContactAdapterState state) {
        setButtonState(state);
        EventBus.getDefault().post(new StateChangedEvent<>(state));
    }

    @Override
    public void setContactsList(List<Contact.ContactSimple> contacts) {
        adapter = new ContactAdapter(getLayoutInflater(), contacts);
        listView.setAdapter(adapter);
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public List<Contact.ContactSimple> getRemoveList() {
        SparseBooleanArray array = listView.getCheckedItemPositions();
        List<Contact.ContactSimple> removeList = new ArrayList<>();
        for (int i = 0; i < array.size(); ++i) {
            if (array.get(i)) {
                removeList.add((Contact.ContactSimple) listView.getItemAtPosition(i));
            }
        }
        return removeList;
    }

    @Override
    protected void onDestroy() {
        clearChecks();
        super.onDestroy();
    }
}
