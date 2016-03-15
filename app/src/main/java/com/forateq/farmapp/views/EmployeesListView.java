package com.forateq.farmapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.forateq.farmapp.R;
import com.forateq.farmapp.adapters.EmployeeCustomAdapter;
import com.forateq.farmapp.fragments.ERPFragment;
import com.forateq.farmapp.models.EmployeesModel;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

/**
 * Created by Vallejos Family on 3/7/2016.
 */
public class EmployeesListView extends LinearLayout implements View.OnClickListener {

    private ImageView backImageView;
    private ListView employeesContainer;
    private EditText searchEmployeesEditText;
    private EmployeeCustomAdapter adapter;
    private FloatingActionButton fab;
    private List<EmployeesModel> employeesModels;

    public EmployeesListView(Context context, List<EmployeesModel> employeesModels) {
        super(context);
        this.employeesModels = employeesModels;
        init();
    }

    public EmployeesListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmployeesListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.employee_list_view, this);
        employeesContainer = (ListView) findViewById(R.id.employees_container);
        backImageView = (ImageView) findViewById(R.id.back);
        backImageView.setOnClickListener(this);
        searchEmployeesEditText = (EditText) findViewById(R.id.search);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        adapter = new EmployeeCustomAdapter(getContext(), this, employeesModels, employeesContainer);
        employeesContainer.setAdapter(adapter);
        fab.attachToListView(employeesContainer);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:{
                ERPFragment.backView();
                break;
            }
        }
    }
}
