package com.projects.todo;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabseHelper db;
    private TaskAdapter adapter;
    private ArrayList<String> taskList;
    private ArrayList<Integer> idList;
    private EditText editTextTask;
    private ListView listViewTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabseHelper(this);
        taskList = new ArrayList<>();
        idList = new ArrayList<>();
        adapter = new TaskAdapter(this,taskList);

        editTextTask = findViewById(R.id.editTextTask);
        listViewTasks = findViewById(R.id.listViewTasks);
        Button buttonAdd = findViewById(R.id.buttonAdd);

        listViewTasks.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                markTaskAsComplete(position);
            }
        });
        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                deleteTask(position);
                return true;
            }
        });
        loadTasks();
    }

    private void addTask() {
        String task = editTextTask.getText().toString();
        if(!task.isEmpty()){
            if(db.insertTask(task)){
                Toast.makeText(MainActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
                editTextTask.setText("");
                loadTasks();
            }
            else {
                Toast.makeText(MainActivity.this, "Error Adding Task", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Please Enter a Task", Toast.LENGTH_SHORT).show();
        }
    }
    private void markTaskAsComplete(int position){
        int taskId = idList.get(position);
        db.updateTaskStatus(taskId,1);
        Toast.makeText(MainActivity.this, "Task Completed", Toast.LENGTH_SHORT).show();
        loadTasks();
    }
    private void deleteTask(int position){
        int taskId = idList.get(position);
        db.deleteTask(taskId);
        Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
        loadTasks();
    }
    private void loadTasks(){
        taskList.clear();
        idList.clear();
        Cursor cursor = db.getAllTasks();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String task = cursor.getString(1);
                int status = cursor.getInt(2);
                if(status == 0){
                    taskList.add(task);
                    idList.add(id);
                }
            } while (cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }
}