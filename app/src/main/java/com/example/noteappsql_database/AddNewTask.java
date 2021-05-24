package com.example.noteappsql_database;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.noteappsql_database.Model.ToDoModel;
import com.example.noteappsql_database.Utils.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG="AddnewTask";

    //Widgets
    private EditText mEditText;
    private Button saveButton;
    private DatabaseHelper myDB;

    public static AddNewTask newInstance(){

        return new AddNewTask();
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_new_task,container,false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText=view.findViewById(R.id.editText);
        saveButton=view.findViewById(R.id.buttonSaved);

        myDB=new DatabaseHelper(getActivity());


        boolean isUpdate=false;


        Bundle bundle=getArguments();
        if (bundle!=null){
            isUpdate=true;
            String task=bundle.getString("task");
            mEditText.setText(task);

            if (task.length()>0){
                saveButton.setEnabled(true);
            }
        }

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().equals("")){
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(Color.GRAY);
                }else {
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text=mEditText.getText().toString();

                if (finalIsUpdate){

                    myDB.updateTask(bundle.getInt("id"),text);
                }else {
                    ToDoModel item=new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    myDB.inserTask(item);

                }
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);

        Activity activity=getActivity();

        if (activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
