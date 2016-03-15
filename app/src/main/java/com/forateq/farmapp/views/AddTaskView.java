package com.forateq.farmapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.forateq.farmapp.R;
import com.forateq.farmapp.adapters.NothingSelectedSpinnerAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by Vallejos Family on 3/4/2016.
 */
public class AddTaskView extends LinearLayout {

    private EditText taskNameEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private EditText budgetEditText;
    private EditText durationEditText;
    private EditText scopeDescriptionEditText;
    private Spinner personResponsibleSpinner;
    private Map<String, Integer> employeeIdAndNameMap;
    private List<String> nameLists;

    public AddTaskView(Context context, Map<String, Integer> employeeIdAndNameMap, List<String> nameLists) {
        super(context);
        this.employeeIdAndNameMap = employeeIdAndNameMap;
        this.nameLists = nameLists;
        init();
    }

    public AddTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddTaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.add_task_view, this);
        taskNameEditText = (EditText) findViewById(R.id.task_name);
        startDateEditText = (EditText) findViewById(R.id.task_start_date);
        endDateEditText = (EditText) findViewById(R.id.task_end_date);
        budgetEditText = (EditText) findViewById(R.id.task_budget);
        durationEditText = (EditText) findViewById(R.id.task_duration);
        scopeDescriptionEditText = (EditText) findViewById(R.id.task_scope);
        personResponsibleSpinner = (Spinner) findViewById(R.id.person_responsible);
        ArrayAdapter<String> nameAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, nameLists);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personResponsibleSpinner.setAdapter(nameAdapter);
        personResponsibleSpinner.setPrompt("Person Responsible");
        personResponsibleSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        nameAdapter,
                        R.layout.nothing_selected,
                        getContext()));
    }

    public int getEmployeeId(String name){
        return employeeIdAndNameMap.get(name);
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

    public Spinner getPersonResponsibleSpinner() {
        return personResponsibleSpinner;
    }

    public EditText getScopeDescriptionEditText() {
        return scopeDescriptionEditText;
    }
}
