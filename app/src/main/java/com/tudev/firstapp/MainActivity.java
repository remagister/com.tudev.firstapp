package com.tudev.firstapp;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.view.ViewBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ViewBase implements IMainView {

    private BaseAdapter adapter;
    private IMainPresenter presenter;
    @BindView(R.id.listViewOne) ListView listView;
    @BindView(R.id.listActionButton) Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter.onCreate(getApplicationContext());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.itemClicked((Contact.ContactSimple) adapterView.getItemAtPosition(i));
            }
        });

        actionButton.setTag(AddButtonIntent.ADD);

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                actionButton.setText(MainActivity.this.getString(R.string.remove_button));
                actionButton.setTag(AddButtonIntent.REMOVE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                actionButton.setText(MainActivity.this.getString(R.string.add_button));
                actionButton.setTag(AddButtonIntent.ADD);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.buttonClicked((AddButtonIntent) actionButton.getTag());
            }
        });
    }

    @Override
    protected void onResume() {
        // user re-enters activity here
        presenter.onResume();
        super.onResume();
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
        adapter.notifyDataSetChanged();
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
