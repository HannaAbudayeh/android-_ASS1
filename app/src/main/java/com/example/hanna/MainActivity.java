package com.example.hanna;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;
    Button addbtt;
    EditText editText;
    static List<String> Task;
    static ArrayAdapter<String> arrayAdapter;
    //    RadioButton low;
    RadioButton high;
    //    RadioButton medium;
    RadioGroup group;
    ListView Lview;
    public String taskPrii ="";
    String taskdel;
    String delTaskName;
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSharedPrefs();
        loadData();
        setupViews();
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, Task);
        Lview.setAdapter(arrayAdapter);
//        clearSharedPreferences();

        taskdel = getIntent().getStringExtra("deleteTask");
        delTaskName = getIntent().getStringExtra("selectedTask");

        if ("true".equalsIgnoreCase(taskdel)) {
//            Task.remove(Integer.parseInt(getIntent().getStringExtra("position")));
            Task.remove(delTaskName);
            saveData();
            arrayAdapter.notifyDataSetChanged();
            editor.remove("selectedTask");
            editor.apply();
            saveData();
        }
        errorTextView = findViewById(R.id.hannaa);

        addbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = group.getCheckedRadioButtonId();

                if (selectedId == R.id.highR) {
                    taskPrii = "High";
                } else if (selectedId == R.id.mediumR) {
                    taskPrii = "Medium";
                } else if (selectedId == R.id.lowR) {
                    taskPrii = "Low";
                }

                String tasks = editText.getText().toString();
                if (tasks != null && !tasks.isEmpty()) {
                    if (taskPrii != null && !taskPrii.isEmpty()) {
                        Task.add(tasks);
                        saveTaskPri(tasks, taskPrii);
                        arrayAdapter.notifyDataSetChanged();
                        saveData();
                    } else {
                        errorTextView.setText("choose priority");
                    }
                } else {
                    errorTextView.setText("Write Name");
                }
            }
        });
        Lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = Task.get(position);

                String selectedPriority = getTaskPri(selected);
                String selectedSta = getTaskSta(selected);

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("task_Priority_", selectedPriority);
                intent.putExtra("task_name", selected);
                intent.putExtra("task_Status", selectedSta);
                intent.putExtra("position", position);

                Log.d("logid",selectedPriority);
                startActivity(intent);
            }
        });

    }

    private void clearSharedPreferences() {
        editor.clear();
        editor.apply();
    }

    private String getTaskPri(String task) {
        return prefs.getString("task_Priority_" + task, "");

    }

    private static String getTaskSta(String task) {
        return prefs.getString("task_Status_" + task, "");

    }
    private void saveTaskPri(String task, String  priority) {
        editor.putString("task_Priority_" + task, priority);
        editor.putString("task_Status_" + task, "unfinished");
        editor.apply();
    }

    private void setupSharedPrefs() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    private static void saveData() {
        Gson gson = new Gson();
        String json = gson.toJson(Task);
        editor.putString("list_task", json);
        editor.apply();
    }

    private void loadData() {
        Gson gson = new Gson();
        String json = prefs.getString("list_task", null);
        if (json != null) {
            Task = gson.fromJson(json, ArrayList.class);
        } else {
            Task = new ArrayList<>();
        }
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, Task);
        if (Lview != null) {
            Lview.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }
    }
    @SuppressLint("WrongViewCast")
    private void setupViews() {
        addbtt = findViewById(R.id.addbt);
        editText = findViewById(R.id.editTextText);
        group = findViewById(R.id.radioGroup);
//        low = findViewById(R.id.lowR);
//        high = findViewById(R.id.highR);
//        medium = findViewById(R.id.mediumR);
        Lview = findViewById(R.id.llist);
    }
    public static void toggleTaskStatus(String taskName, String updStatus) {
        for (int i = 0; i < Task.size(); i++) {
            String currTask = Task.get(i);
            if (currTask.equals(taskName)) {
                Task.set(i, taskName + " - " + updStatus);
                break;
            }
        }
    }
    public static void updateTaskStatusInList(String taskName, String updatedStatus) {
        for (int i = 0; i < Task.size(); i++) {
            if (Task.get(i).equals(taskName)) {
                String taskStatus = getTaskSta(taskName);
                if (!taskStatus.equals(updatedStatus)) {
                    saveTaskStatus(taskName, updatedStatus);
                    Task.set(i, taskName);
                    arrayAdapter.notifyDataSetChanged();
                    saveData();
                    break;
                }
            }
        }
    }
    private static void saveTaskStatus(String taskName, String updatedStatus) {
        editor.putString("task_Status_" + taskName, updatedStatus);
        editor.apply();
    }
}