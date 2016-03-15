package com.forateq.farmapp.views;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.afollestad.materialdialogs.MaterialDialog;
import com.forateq.farmapp.FarmAppService;
import com.forateq.farmapp.FarmApplication;
import com.forateq.farmapp.R;
import com.forateq.farmapp.adapters.ProjectCustomAdapter;
import com.forateq.farmapp.adapters.TaskCustomAdapter;
import com.forateq.farmapp.fragments.ERPFragment;
import com.forateq.farmapp.models.EmployeesModel;
import com.forateq.farmapp.models.ProjectsModel;
import com.forateq.farmapp.models.SessionKey;
import com.forateq.farmapp.models.TaskModel;
import com.forateq.farmapp.objects.AddProjectWrapper;
import com.forateq.farmapp.objects.Data;
import com.forateq.farmapp.objects.Employee;
import com.forateq.farmapp.objects.EmployeesWrapper;
import com.forateq.farmapp.objects.ProjectWrapper;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * Created by Vallejos Family on 2/26/2016.
 */
public class ProjectDetailsView extends LinearLayout implements View.OnClickListener {

    private EditText projectNameEditText;
    private EditText projectStartDateEditText;
    private EditText projectEndDateEditText;
    private EditText projectBudgetEditText;
    private TextView projectIdTextView;
    private EditText projectDescriptionEditText;
    private Button saveButton;
    private ListView tasksListView;
    private FloatingActionButton fab;
    private ImageView backImageView;
    private ImageView editImageView;
    private ImageView deleteImageView;
    private TextView labelTextView;
    private String labelString;
    private TaskCustomAdapter adapter;
    private ProjectCustomAdapter projectCustomAdapter;
    private List<TaskModel> tasksWrappers;
    private ProjectsModel projectsModel;
    private ListView projectListView;
    private TextView projectNameTextView;

    public ProjectDetailsView(Context context, String labelString, List<TaskModel> tasksWrappers, ProjectCustomAdapter projectCustomAdapter, ProjectsModel projectsModel, ListView projectListView, TextView projectNameTextView) {
        super(context);
        this.labelString = labelString;
        this.tasksWrappers = tasksWrappers;
        this.projectCustomAdapter = projectCustomAdapter;
        this.projectsModel = projectsModel;
        this.projectListView = projectListView;
        this.projectNameTextView = projectNameTextView;
        init();
    }

    public ProjectDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProjectDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.project_details_view, this);
        projectNameEditText = (EditText) findViewById(R.id.project_name);
        projectStartDateEditText = (EditText) findViewById(R.id.project_start_date);
        projectStartDateEditText.setOnClickListener(this);
        projectEndDateEditText = (EditText) findViewById(R.id.project_end_date);
        projectEndDateEditText.setOnClickListener(this);
        projectBudgetEditText = (EditText) findViewById(R.id.budget);
        projectIdTextView = (TextView) findViewById(R.id.project_id);
        projectDescriptionEditText = (EditText) findViewById(R.id.project_details);
        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(this);
        adapter = new TaskCustomAdapter(getContext(), this, tasksWrappers);
        tasksListView = (ListView) findViewById(R.id.tasks_container);
        tasksListView.setAdapter(adapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(tasksListView);
        fab.setOnClickListener(this);
        backImageView = (ImageView) findViewById(R.id.back);
        backImageView.setOnClickListener(this);
        editImageView = (ImageView) findViewById(R.id.edit);
        editImageView.setOnClickListener(this);
        deleteImageView = (ImageView) findViewById(R.id.delete);
        deleteImageView.setOnClickListener(this);
        labelTextView = (TextView) findViewById(R.id.label);
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

    public EditText getProjectDescriptionEditText() {
        return projectDescriptionEditText;
    }

    public TextView getProjectIdTextView() {
        return projectIdTextView;
    }

    public TextView getLabelTextView() {
        return labelTextView;
    }

    /**
     * use to enable editing of the details of the project
     */
    public void enableEdit(){
        projectNameEditText.setEnabled(true);
        projectDescriptionEditText.setEnabled(true);
        projectBudgetEditText.setEnabled(true);
        projectStartDateEditText.setEnabled(true);
        projectEndDateEditText.setEnabled(true);
        saveButton.setVisibility(VISIBLE);
    }

    /**
     * use to disbale the editing of details of the project
     */
    public void disAbleEdit(){
        projectNameEditText.setEnabled(false);
        projectDescriptionEditText.setEnabled(false);
        projectBudgetEditText.setEnabled(false);
        projectStartDateEditText.setEnabled(false);
        projectEndDateEditText.setEnabled(false);
        saveButton.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.back:{
                ERPFragment.backView();
                break;
            }
            case R.id.edit:{
                enableEdit();
                break;
            }
            case R.id.delete:{
                new MaterialDialog.Builder(getContext())
                        .title("Are you sure you want to delete this Project?")
                        .titleColorRes(R.color.colorText)
                        .backgroundColorRes(R.color.colorPrimaryDark)
                        .widgetColorRes(R.color.colorPrimaryText)
                        .positiveText("Ok")
                        .positiveColorRes(R.color.colorText)
                        .negativeText("Cancel")
                        .negativeColorRes(R.color.colorText)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                new DeleteProjectTask(Integer.parseInt(projectIdTextView.getText().toString())).execute();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                            }
                        })
                        .show();
                break;
            }
            case R.id.save:{
                disAbleEdit();
                new UpdateProjectTask(Integer.parseInt(projectIdTextView.getText().toString()), projectNameEditText.getText().toString(), projectStartDateEditText.getText().toString(), projectEndDateEditText.getText().toString(), Double.parseDouble(projectBudgetEditText.getText().toString()), projectDescriptionEditText.getText().toString(), "0.0", "0.0", projectsModel).execute();
                break;
            }
            case R.id.fab:{
                new GetAllEmployeeTask().execute();
                break;
            }
            case R.id.project_start_date:{
                setDate(projectStartDateEditText);
                break;
            }
            case R.id.project_end_date:{
                setDate(projectEndDateEditText);
                break;
            }
        }
    }

    /**
     * use to update the details of task from the api
     */
    class UpdateProjectTask extends AsyncTask<Void, Void, Void>{

        private ProgressDialog dialog;
        private AddProjectWrapper addProjectWrapper;
        private int projectId;
        private String projectName;
        private String projectStart;
        private String projectEnd;
        private double projectBudget;
        private String projectDescription;
        private String projectLatitude;
        private String projectLongitude;
        private ProjectsModel projectsModel;

        public UpdateProjectTask(int projectId, String projectName, String projectStart, String projectEnd, double projectBudget, String projectDescription, String projectLatitude, String projectLongitude, ProjectsModel projectsModel) {
            this.projectId = projectId;
            this.projectName = projectName;
            this.projectStart = projectStart;
            this.projectEnd = projectEnd;
            this.projectBudget = projectBudget;
            this.projectDescription = projectDescription;
            this.projectLatitude = projectLatitude;
            this.projectLongitude = projectLongitude;
            this.projectsModel = projectsModel;
        }

        @Override
        protected Void doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(FarmAppService.URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            FarmAppService service = restAdapter.create(FarmAppService.class);
            addProjectWrapper = service.updateProject(projectId, projectName, projectStart, projectEnd, projectBudget, projectDescription, projectLatitude, projectLongitude, SessionKey.getSessionKeyValue().getUser(), FarmApplication.DEVICE_ID, SessionKey.getSessionKeyValue().getSession_key(), "put");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Updating project");
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(addProjectWrapper.getResponse().getResponse_text().equals("Operation Successful.")){
                Toast.makeText(getContext(), "Project successfully updated.", Toast.LENGTH_SHORT).show();
                labelTextView.setText(addProjectWrapper.getData().getName());
                Log.e("Project Name: ", projectNameTextView.getText().toString());
                projectNameTextView.setText(addProjectWrapper.getData().getName());
                projectCustomAdapter.notifyDataSetChanged();
                Log.e("Project Name: ", projectNameTextView.getText().toString());
                new GetAllProjectsTask(dialog, "update").execute();
            }
            else{
                Toast.makeText(getContext(), "There is a problem updating the project please try again.", Toast.LENGTH_SHORT).show();
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }
    }

    /**
     * use to delete a project from the api
     */
    class DeleteProjectTask extends AsyncTask<Void, Void, Void>{

        private ProgressDialog dialog;
        private AddProjectWrapper addProjectWrapper;
        private int projectId;

        public DeleteProjectTask(int projectId) {
            this.projectId = projectId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(FarmAppService.URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            FarmAppService service = restAdapter.create(FarmAppService.class);
            addProjectWrapper = service.deleteProject(projectId, SessionKey.getSessionKeyValue().getUser(), FarmApplication.DEVICE_ID, SessionKey.getSessionKeyValue().getSession_key(), "delete");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Deleting project...");
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(addProjectWrapper.getResponse().getResponse_text().equals("Operation Successful.")){
                Toast.makeText(getContext(), "Project successfully deleted.", Toast.LENGTH_SHORT).show();
                new GetAllProjectsTask(dialog, "delete").execute();
            }
            else{
                Toast.makeText(getContext(), "There is a problem deleting the project please try again.", Toast.LENGTH_SHORT).show();
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }
    }

    public void setDate(final EditText editText){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        editText.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    /**
     * use to get all the projects from the api
     */
    class GetAllProjectsTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        private ProjectWrapper projectWrapper;
        private String method;

        public GetAllProjectsTask(ProgressDialog dialog, String method) {
            this.dialog = dialog;
            this.method = method;
        }

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
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(projectWrapper.getResponse().getResponse_text().equals("Operation Successful.")){
                ProjectListView projectListView = new ProjectListView(getContext(), ProjectsModel.getProjectsModel());
                FarmApplication.viewDeque.removeLast();
                if(method.equals("delete")){
                    ERPFragment.changeView(projectListView);
                }
                else{
                    FarmApplication.viewDeque.addLast(projectListView);
                }
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
     * use to get all the employees for the persons responsible when adding a new task
      */
    class GetAllEmployeeTask extends AsyncTask<Void, Void, Void>{

        private ProgressDialog dialog;
        private EmployeesWrapper employeesWrapper;
        private Map<String, Integer> employeeMap;
        private List<String> employeeList;

        public GetAllEmployeeTask() {
            employeeMap = new HashMap<>();
            employeeList = new ArrayList<>();
        }

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
                employeeMap.put(employee.getFirst_name() + " " + employee.getLast_name(), employee.getId());
                employeeList.add(employee.getFirst_name() + " " + employee.getLast_name());
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
                final AddTaskView addTaskView = new AddTaskView(getContext(), employeeMap, employeeList);
                addTaskView.getStartDateEditText().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDate(addTaskView.getStartDateEditText());
                    }
                });
                addTaskView.getEndDateEditText().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDate(addTaskView.getEndDateEditText());
                    }
                });
                final MaterialDialog.Builder createNoteDialog = new MaterialDialog.Builder(getContext())
                        .title("Add Task")
                        .titleColorRes(R.color.colorText)
                        .backgroundColorRes(R.color.colorPrimaryDark)
                        .widgetColorRes(R.color.colorPrimaryText)
                        .customView(addTaskView, true)
                        .positiveText("Ok")
                        .positiveColorRes(R.color.colorText)
                        .negativeText("Cancel")
                        .negativeColorRes(R.color.colorText)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                Log.e("Employee Id", "" + addTaskView.getEmployeeId(addTaskView.getPersonResponsibleSpinner().getSelectedItem().toString()));
                                new AddProjectTask(addTaskView.getTaskNameEditText().getText().toString(), addTaskView.getStartDateEditText().getText().toString(), addTaskView.getEndDateEditText().getText().toString(), Double.parseDouble(addTaskView.getBudgetEditText().getText().toString()), addTaskView.getScopeDescriptionEditText().getText().toString(), Integer.parseInt(addTaskView.getDurationEditText().getText().toString()), Integer.parseInt(projectIdTextView.getText().toString()), addTaskView.getEmployeeId(addTaskView.getPersonResponsibleSpinner().getSelectedItem().toString())).execute();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                            }
                        });
                final MaterialDialog addNoteDialog = createNoteDialog.build();
                addNoteDialog.show();
            }
            else{
                Toast.makeText(getContext(), "There is a problem getting all the employees please try again.", Toast.LENGTH_SHORT).show();
            }
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }

    /**
     * use to add task from the api
     */
    class AddProjectTask extends AsyncTask<Void, Void, Void>{

        private AddProjectWrapper addProjectWrapper;
        private ProgressDialog dialog;
        private String taskName;
        private String startDate;
        private String endDate;
        private double taskBudget;
        private String description;
        private int taskDuration;
        private int projectId;
        private int personResponsibleId;

        public AddProjectTask(String taskName, String startDate, String endDate, double taskBudget, String description, int taskDuration, int projectId, int personResponsibleId) {
            this.taskName = taskName;
            this.startDate = startDate;
            this.endDate = endDate;
            this.taskBudget = taskBudget;
            this.description = description;
            this.taskDuration = taskDuration;
            this.projectId = projectId;
            this.personResponsibleId = personResponsibleId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(FarmAppService.URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            FarmAppService service = restAdapter.create(FarmAppService.class);
            addProjectWrapper = service.createTask(taskName, startDate, endDate, taskBudget, description, "0.0", "0.0", taskDuration, projectId, personResponsibleId, SessionKey.getSessionKeyValue().getUser(), FarmApplication.DEVICE_ID, SessionKey.getSessionKeyValue().getSession_key());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Adding task...");
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(addProjectWrapper.getResponse().getResponse_text().equals("Operation Successful.")){
                Toast.makeText(getContext(), "Task Successfully added.", Toast.LENGTH_SHORT).show();
                TaskModel taskModel = new TaskModel();
                taskModel.setProject_id(projectId);
                taskModel.setTask_name(addProjectWrapper.getData().getName());
                taskModel.setTask_id(addProjectWrapper.getData().getId());
                taskModel.save();
                adapter.addItem(taskModel);
            }
            else{
                Toast.makeText(getContext(), "There is a problem adding task please try again", Toast.LENGTH_SHORT).show();
            }
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
