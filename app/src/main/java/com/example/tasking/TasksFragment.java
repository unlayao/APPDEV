package com.example.tasking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TasksFragment extends Fragment {

    private Button addRemove;
    private FloatingActionButton btnEdit;
    private Spinner taskData;
    private EditText inputTask;
    private boolean isEditing = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        addRemove = view.findViewById(R.id.btnadd);
        btnEdit = view.findViewById(R.id.addtask);
        taskData = view.findViewById(R.id.taskdata);
        inputTask = view.findViewById(R.id.inputtask);


        inputTask.setVisibility(View.INVISIBLE);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEditing){
                    taskData.setVisibility(View.INVISIBLE);
                    inputTask.setVisibility(View.VISIBLE);
                    addRemove.setText("ADD");
                    isEditing = true;
                }else{
                    taskData.setVisibility(View.VISIBLE);
                    inputTask.setVisibility(View.INVISIBLE);
                    addRemove.setText("REMOVE");
                    isEditing = false;
                }
            }
        });
        //IMPORTANT FOR FUNCTIONALITY OF FRAGMENTS
        return view;
    }

}
