package com.example.hanna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    //    TextView tx_name,tx_priority;
    private String taskName;
    private String taskPriority;
    private String taskSta;
    //    Switch s_switch;
    Button back;
    Button togglebtn;
    private RadioButton unfinished;
    private RadioButton finished;
    //    TextView namee;
//    TextView prii;
    Button deleteButton;
    TextView finn;
    String positionn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        togglebtn = findViewById(R.id.togg);
//        namee = findViewById(R.id.name);
//        prii= findViewById(R.id.priority);
        unfinished = findViewById(R.id.unfinished);
        finished = findViewById(R.id.finished);
        finn = findViewById(R.id.fin);

        positionn = getIntent().getStringExtra("position");


        taskName = getIntent().getStringExtra("task_name");
        TextView txt = findViewById(R.id.name);
        txt.setText(taskName);

        taskPriority = getIntent().getStringExtra("task_Priority_");
        TextView txtt = findViewById(R.id.priority);
        txtt.setText(taskPriority);

        taskSta = getIntent().getStringExtra("task_Status");
        if ("unfinished".equalsIgnoreCase(taskSta)) {
            unfinished.setChecked(true);
        }else if("finished".equalsIgnoreCase(taskSta)) {
            finished.setChecked(true);

        }

        togglebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updStatus = finished.isChecked() ? "finished" : "unfinished";
                MainActivity.updateTaskStatusInList(taskName, updStatus);
                if ("finished".equalsIgnoreCase(updStatus)) {
                    finn.setText("Task status updated to finished");

                } else {
                    finn.setText("Task status updated to unfinished");
                }
            }
        });

        deleteButton = findViewById(R.id.del);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(MainActivity2.this, MainActivity.class);
                resultIntent.putExtra("position",positionn);
                resultIntent.putExtra("deleteTask", "true");
                resultIntent.putExtra("selectedTask", taskName);

                startActivity(resultIntent);
            }
        });
        back =findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent1);
//                onBackPressed();
            }
        });
    }

}