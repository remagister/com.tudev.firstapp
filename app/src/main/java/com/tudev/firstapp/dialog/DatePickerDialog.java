package com.tudev.firstapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.tudev.firstapp.R;
import com.tudev.firstapp.calendarpicker.ScrollPicker;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arseniy on 07.09.16.
 */

public class DatePickerDialog extends AppCompatDialogFragment {

    public static final String DATE_KEY = "DATE_KEY$";

    private OnDateSelectedListener listener;
    private OnDateSelectedListener cacheListener;

    private ScrollPicker picker;
    private Date date;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            date = (Date) savedInstanceState.getSerializable(DATE_KEY);
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_calendar, null, false);
        picker = (ScrollPicker) layout.findViewById(R.id.dateScrollPicker);

        if(date != null){
            picker.setDate(date);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.datePicker_title)
                .setView(layout)
                .setPositiveButton(R.string.datePicker_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(listener != null){
                            listener.onDateSelected(picker.getDate());
                        }
                    }
                })
                .setNegativeButton(R.string.datePicker_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(cacheListener != null){
                            cacheListener.onDateSelected(picker.getDate());
                        }
                        getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if(cacheListener != null){
            cacheListener.onDateSelected(picker.getDate());
        }
        super.onCancel(dialog);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DATE_KEY, picker.getDate());
        super.onSaveInstanceState(outState);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener){
        this.listener = listener;
    }

    public void setOnDateDismissedListener(OnDateSelectedListener listener){
        this.cacheListener = listener;
    }

    public void setDate(Date date){
        this.date = date;
        if(picker != null) {
            picker.setDate(date);
        }
    }
}
