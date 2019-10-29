package com.example.testdb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Student> students;
    ListView lvStudents;

    int currentIndex = -1;

    Button btnAdd, btnEdit;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);
        db.CreateTable();

        lvStudents = findViewById(R.id.lvStudents);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);

        /*Student student = new Student(123, "nam anh 1");
        db.InsertStudent(student);

        Student student1 = new Student(124, "nam anh 2");
        db.InsertStudent(student1);

        Student student2 = new Student(125, "nam anh 3");
        db.InsertStudent(student2);*/
        students = db.students();

        final ArrayAdapter<Student> studentArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, students);

        lvStudents.setAdapter(studentArrayAdapter);

        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentIndex = i;
            }
        });

        lvStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure to delete?")
                        .setMessage("Delete this customer?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.DeleteStudent(students.get(currentIndex).getId());
                                students.remove(currentIndex);
                                studentArrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

                return false;
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = getLayoutInflater();

                View addLayout = layoutInflater.inflate(R.layout.edit_dialog,null);

                final EditText Name = addLayout.findViewById(R.id.edtEName);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Add student");
                alert.setView(addLayout);
                alert.setCancelable(false);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Student newstudent = new Student(Name.getText().toString());

                        students.add(newstudent);
                        studentArrayAdapter.notifyDataSetChanged();

                        db.InsertStudent(newstudent);
                    }
                });

                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = alert.create();

                dialog.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = getLayoutInflater();
                View editLayout = layoutInflater.inflate(R.layout.edit_dialog,null);

                final EditText edtEName = editLayout.findViewById(R.id.edtEName);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Edit student");
                alert.setView(editLayout);
                alert.setCancelable(false);

                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this, "In Edit", Toast.LENGTH_SHORT).show();
                        if(currentIndex != -1) {
                            Student student = students.get(currentIndex);
                            student.setName(edtEName.getText().toString());

                            db.UpdateStudent(student);

                            studentArrayAdapter.notifyDataSetChanged();
                        }
                    }
                });

                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = alert.create();

                dialog.show();
            }
        });
    }


}
