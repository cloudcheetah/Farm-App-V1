package com.forateq.farmapp.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.forateq.farmapp.R;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Vallejos Family on 3/4/2016.
 */
public class TaskDetailsView extends LinearLayout implements View.OnClickListener {

    private EditText taskNameEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private EditText budgetEditText;
    private EditText durationEditText;
    private EditText taskDescription;
    private Button saveButton;
    private Button resourcesNeededButton;
    private ListView subTasksListView;
    private Spinner personResponsible;
    private FloatingActionButton fab;
    private ImageView backImageView;
    private ImageView editImageView;
    private ImageView deleteImageView;
    private TextView labelTextView;
    private EditText searchEditText;

    public TaskDetailsView(Context context) {
        super(context);
        init();
    }

    public TaskDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TaskDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.task_details_view, this);
        taskNameEditText = (EditText) findViewById(R.id.task_name);
        startDateEditText = (EditText) findViewById(R.id.task_start_date);
        endDateEditText = (EditText) findViewById(R.id.task_end_date);
        budgetEditText = (EditText) findViewById(R.id.task_budget);
        durationEditText = (EditText) findViewById(R.id.task_duration);
        taskDescription = (EditText) findViewById(R.id.task_description);
        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(this);
        resourcesNeededButton = (Button) findViewById(R.id.task_resources);
        resourcesNeededButton.setOnClickListener(this);
        subTasksListView = (ListView) findViewById(R.id.sub_tasks_container);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        backImageView = (ImageView) findViewById(R.id.back);
        backImageView.setOnClickListener(this);
        editImageView = (ImageView) findViewById(R.id.edit);
        editImageView.setOnClickListener(this);
        deleteImageView = (ImageView) findViewById(R.id.delete);
        deleteImageView.setOnClickListener(this);
        searchEditText = (EditText) findViewById(R.id.search);
        personResponsible = (Spinner) findViewById(R.id.person_responsible);
        setTaskSearch();
        labelTextView = (TextView) findViewById(R.id.label);
    }

    public void setTaskSearch(){
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public EditText getTaskNameEditText() {
        return taskNameEditText;
    }

    public EditText getStartDateEditText() {
        return startDateEditText;
    }

    public EditText getEndDateEditText() {
        return endDateEditText;
    }

    public EditText getBudgetEditText() {
        return budgetEditText;
    }

    public EditText getDurationEditText() {
        return durationEditText;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public EditText getTaskDescription() {
        return taskDescription;
    }

    public TextView getLabelTextView() {
        return labelTextView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab:{
                break;
            }
            case R.id.back:{
                break;
            }
            case R.id.edit:{
                break;
            }
            case R.id.delete:{
                break;
            }
            case R.id.save:{
                break;
            }
            case R.id.task_resources:{
                break;
            }
        }
    }
}
