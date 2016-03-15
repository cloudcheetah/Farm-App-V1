package com.forateq.farmapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.forateq.farmapp.R;

/**
 * Created by Vallejos Family on 2/26/2016.
 */
public class AddProjectView extends LinearLayout {

    private EditText projectNameEditText;
    private EditText projectStartDateEditText;
    private EditText projectEndDateEditText;
    private EditText projectBudgetEditText;
    private EditText projectDetailsEditText;

    public AddProjectView(Context context) {
        super(context);
        init();
    }

    public AddProjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddProjectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.add_project_view, this);
        projectNameEditText = (EditText) findViewById(R.id.project_name);
        projectStartDateEditText = (EditText) findViewById(R.id.start_date);
        projectEndDateEditText = (EditText) findViewById(R.id.end_date);
        projectBudgetEditText = (EditText) findViewById(R.id.project_budget);
        projectDetailsEditText = (EditText) findViewById(R.id.project_description);
    }

    public EditText getProjectNameEditText() {
        return projectNameEditText;
    }

    public EditText getProjectStartDateEditText() {
        return projectStartDateEditText;
    }

    public EditText getProjectEndDateEditText() {
        return projectEndDateEditText;
    }

    public EditText getProjectBudgetEditText() {
        return projectBudgetEditText;
    }

    public EditText getProjectDetailsEditText() {
        return projectDetailsEditText;
    }
}
