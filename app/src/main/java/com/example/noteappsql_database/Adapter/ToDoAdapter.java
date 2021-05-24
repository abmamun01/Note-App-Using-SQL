package com.example.noteappsql_database.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteappsql_database.AddNewTask;
import com.example.noteappsql_database.MainActivity;
import com.example.noteappsql_database.Model.ToDoModel;
import com.example.noteappsql_database.R;
import com.example.noteappsql_database.Utils.DatabaseHelper;

import java.util.List;


public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.TodoHolder> {

    List<ToDoModel> list;
    private MainActivity activity;
    private DatabaseHelper myDB;

    public ToDoAdapter(MainActivity activity, DatabaseHelper myDB) {
        this.activity = activity;
        this.myDB = myDB;
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ToDoAdapter.TodoHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false   );
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ToDoAdapter.TodoHolder holder, int position) {

        holder.mCheckbox.setText(list.get(position).getTask());
        holder.mCheckbox.setChecked(toBoolean((list.get(position).getStatus())));

        holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    myDB.updateStatus(list.get(position).getId(),1);
                }else {
                    myDB.updateStatus(list.get(position).getId(),0);
                }
            }
        });

    }

    public Context getContext(){
        return activity;
    }

    public void setTask(List<ToDoModel> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){

        ToDoModel item=list.get(position);
        myDB.deleteTask(item.getId());
        list.remove(position);
        notifyDataSetChanged();

    }

    public void editItem(int position){
        ToDoModel item=list.get(position);

        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());


        AddNewTask task=new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());


    }



    public boolean toBoolean(int num){
        return num!=0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TodoHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckbox;

        public TodoHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            mCheckbox=itemView.findViewById(R.id.mCheckbox);

        }
    }
}
