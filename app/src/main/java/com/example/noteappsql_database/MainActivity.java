package com.example.noteappsql_database;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.noteappsql_database.Adapter.ToDoAdapter;
import com.example.noteappsql_database.Model.ToDoModel;
import com.example.noteappsql_database.Utils.DatabaseHelper;
import com.example.noteappsql_database.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener{

    ActivityMainBinding binding;
    private DatabaseHelper myDB;
    private List<ToDoModel> list;
    private ToDoAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot() );

        myDB=new DatabaseHelper(MainActivity.this);
        list=new ArrayList<>();
        adapter=new ToDoAdapter(MainActivity.this,myDB);


        binding.recyclerViewID.setHasFixedSize(true);
        binding.recyclerViewID.setLayoutManager(new LinearLayoutManager(this));
        list=myDB.getAllTasks();
        Collections.reverse(list);
        adapter.setTask(list);;

        binding.recyclerViewID.setAdapter(adapter);


        binding.fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewID);





    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {

        list=myDB.getAllTasks();
        Collections.reverse(list);
        adapter.setTask(list);;
        adapter.notifyDataSetChanged();

    }
}