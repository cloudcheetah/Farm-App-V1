package com.forateq.farmapp.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.forateq.farmapp.FarmAppService;
import com.forateq.farmapp.FarmApplication;
import com.forateq.farmapp.R;
import com.forateq.farmapp.fragments.ERPFragment;
import com.forateq.farmapp.models.EmployeesModel;
import com.forateq.farmapp.models.ProjectsModel;
import com.forateq.farmapp.models.SessionKey;
import com.forateq.farmapp.objects.Data;
import com.forateq.farmapp.objects.Employee;
import com.forateq.farmapp.objects.EmployeesWrapper;
import com.forateq.farmapp.objects.ProjectWrapper;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by Vallejos Family on 2/24/2016.
 */
public class MainMenuView extends LinearLayout implements View.OnClickListener {

    private LinearLayout projectLayout;
    private LinearLayout payeesLayout;
    private LinearLayout payersLayout;
    private LinearLayout resourcesLayout;
    private LinearLayout laborLayout;
    private List<ProjectsModel> projects;

    public MainMenuView(Context context) {
        super(context);
        init();
    }

    public MainMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.main_menu_view, this);
        projectLayout = (LinearLayout) findViewById(R.id.projects);
        projectLayout.setOnClickListener(this);
        payeesLayout = (LinearLayout) findViewById(R.id.payees);
        payeesLayout.setOnClickListener(this);
        payersLayout = (LinearLayout) findViewById(R.id.payers);
        payersLayout.setOnClickListener(this);
        laborLayout = (LinearLayout) findViewById(R.id.labor);
        laborLayout.setOnClickListener(this);
        resourcesLayout = (LinearLayout) findViewById(R.id.resources);
        resourcesLayout.setOnClickListener(this);
    }

    public LinearLayout getProjectLayout() {
        return projectLayout;
    }

    public LinearLayout getPayeesLayout() {
        return payeesLayout;
    }

    public LinearLayout getPayersLayout() {
        return payersLayout;
    }

    public LinearLayout getResourcesLayout() {
        return resourcesLayout;
    }

    public LinearLayout getLaborLayout() {
        return laborLayout;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.projects:{
                new GetAllProjectsTask().execute();
                break;
            }
            case R.id.payees:{
                break;
            }
            case R.id.payers:{
                break;
            }
            case R.id.labor:{
                new GetAllEmployeeTask().execute();
                break;
            }
            case R.id.resources:{
                break;
            }
        }
    }

    /**
     * use to get all the list of projects from the api
     */
    class GetAllProjectsTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        private ProjectWrapper projectWrapper;


        @Override
        protected Void doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(FarmAppService.URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            FarmAppService service = restAdapter.create(FarmAppService.class);
            projectWrapper = service.getAllProjects(SessionKey.getSessionKeyValue().getUser(), FarmApplication.DEVICE_ID, SessionKey.getSessionKeyValue().getSession_key());
            new Delete().from(ProjectsModel.class).execute();
            for(Data data : projectWrapper.getData()){
                ProjectsModel projectsModel = new ProjectsModel();
                projectsModel.setProjectId(data.getId());
                projectsModel.setProjectName(data.getName());
                projectsModel.save();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Getting projects...");
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(projectWrapper.getResponse().getResponse_text().equals("Operation Successful.")){
                ProjectListView projectListView = new ProjectListView(getContext(), ProjectsModel.getProjectsModel());
                FarmApplication.viewDeque.addLast(MainMenuView.this);
                ERPFragment.changeView(projectListView);
            }
            else{
                Toast.makeText(getContext(), "There is a problem getting all the projects please try again.", Toast.LENGTH_SHORT).show();
            }
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }

    /**
     * use to get the list of employees from the api
     */
    class GetAllEmployeeTask extends AsyncTask<Void, Void, Void>{

        private ProgressDialog dialog;
        private EmployeesWrapper employeesWrapper;

        @Override
        protected Void doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(FarmAppService.URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            FarmAppService service = restAdapter.create(FarmAppService.class);
            employeesWrapper = service.getAllEmployees(SessionKey.getSessionKeyValue().getUser(), FarmApplication.DEVICE_ID, SessionKey.getSessionKeyValue().getSession_key());
            new Delete().from(EmployeesModel.class).execute();
            for(Employee employee : employeesWrapper.getData()){
                EmployeesModel employeesModel = new EmployeesModel();
                employeesModel.setEmployee_id(employee.getId());
                employeesModel.setEmployee_name(employee.getFirst_name() + " " + employee.getLast_name());
                employeesModel.save();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Getting employees...");
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(employeesWrapper.getResponse().getResponse_text().equals("Operation Successful.")){
                EmployeesListView employeesListView = new EmployeesListView(getContext(), EmployeesModel.getAllEmployees());
                FarmApplication.viewDeque.addLast(MainMenuView.this);
                ERPFragment.changeView(employeesListView);
            }
            else{
                Toast.makeText(getContext(), "There is a problem getting all the employees please try again.", Toast.LENGTH_SHORT).show();
            }
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
