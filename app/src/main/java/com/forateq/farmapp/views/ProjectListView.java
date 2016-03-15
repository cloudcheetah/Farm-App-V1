package com.forateq.farmapp.views;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.forateq.farmapp.FarmAppService;
import com.forateq.farmapp.FarmApplication;
import com.forateq.farmapp.R;
import com.forateq.farmapp.adapters.ProjectCustomAdapter;
import com.forateq.farmapp.fragments.ERPFragment;
import com.forateq.farmapp.models.ProjectsModel;
import com.forateq.farmapp.models.SessionKey;
import com.forateq.farmapp.objects.AddProjectWrapper;
import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by Vallejos Family on 2/25/2016.
 */
public class ProjectListView extends LinearLayout implements View.OnClickListener {

    private EditText searchEditText;
    private ListView projectContainerListView;
    private FloatingActionButton addProjectFab;
    private ProjectCustomAdapter adapter;
    private ImageView backView;
    private Context context;
    private List<ProjectsModel> projects;

    public ProjectListView(Context context, List<ProjectsModel> projects) {
        super(context);
        this.context = context;
        this.projects = projects;
        init();
    }

    public ProjectListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ProjectListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.project_list_view, this);
        searchEditText = (EditText) findViewById(R.id.search);
        projectContainerListView = (ListView) findViewById(R.id.projects_container);
        addProjectFab = (FloatingActionButton) findViewById(R.id.fab);
        adapter = new ProjectCustomAdapter(context, this, projects, projectContainerListView);
        projectContainerListView.setAdapter(adapter);
        addProjectFab.attachToListView(projectContainerListView);
        addProjectFab.setOnClickListener(this);
        backView = (ImageView) findViewById(R.id.back);
        backView.setOnClickListener(this);
        setSearchForProjects();
    }

    public void setSearchForProjects(){
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchString = searchEditText.getText().toString();
                    adapter.clearItems();
                    for(ProjectsModel projectsModel : ProjectsModel.getSearchProject(searchString)){
                        adapter.addItem(projectsModel);
                    }
            }
        });
    }

    public ImageView getBackView() {
        return backView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.fab:{
                final AddProjectView addProjectView = new AddProjectView(getContext());
                addProjectView.getProjectStartDateEditText().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDate(addProjectView.getProjectStartDateEditText());
                    }
                });
                addProjectView.getProjectEndDateEditText().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setDate(addProjectView.getProjectEndDateEditText());
                    }
                });
                final MaterialDialog.Builder createNoteDialog = new MaterialDialog.Builder(getContext())
                        .title("Add project")
                        .titleColorRes(R.color.colorText)
                        .backgroundColorRes(R.color.colorPrimaryDark)
                        .widgetColorRes(R.color.colorPrimaryText)
                        .customView(addProjectView, true)
                        .positiveText("Ok")
                        .positiveColorRes(R.color.colorText)
                        .negativeText("Cancel")
                        .negativeColorRes(R.color.colorText)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                new CreateProjectTask(addProjectView.getProjectNameEditText().getText().toString(), addProjectView.getProjectStartDateEditText().getText().toString(), addProjectView.getProjectEndDateEditText().getText().toString(), addProjectView.getProjectBudgetEditText().getText().toString(), addProjectView.getProjectDetailsEditText().getText().toString(), "0.0", "0.0").execute();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                            }
                        });
                final MaterialDialog addNoteDialog = createNoteDialog.build();
                addNoteDialog.show();
                break;
            }

            case R.id.back:{
                ERPFragment.backView();
                break;
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
     * use to create a project from the api
     */
    class CreateProjectTask extends AsyncTask<Void, Void, Void>{

        private ProgressDialog dialog;
        private AddProjectWrapper addProjectWrapper;
        private String projectName;
        private String projectStart;
        private String projectEnd;
        private String projectBudget;
        private String projectDescription;
        private String projectLatitude;
        private String projectLongitude;

        public CreateProjectTask(String projectName, String projectStart, String projectEnd, String projectBudget, String projectDescription, String projectLatitude, String projectLongitude) {
            this.projectName = projectName;
            this.projectStart = projectStart;
            this.projectEnd = projectEnd;
            this.projectBudget = projectBudget;
            this.projectDescription = projectDescription;
            this.projectLatitude = projectLatitude;
            this.projectLongitude = projectLongitude;
        }

        @Override
        protected Void doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(FarmAppService.URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            FarmAppService service = restAdapter.create(FarmAppService.class);
            addProjectWrapper = service.createProject(projectName, projectStart, projectEnd, projectBudget, projectDescription, projectLatitude, projectLongitude, SessionKey.getSessionKeyValue().getUser(), FarmApplication.DEVICE_ID, SessionKey.getSessionKeyValue().getSession_key());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Creating project");
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(addProjectWrapper.getResponse().getResponse_text().equals("Operation Successful.")){
                ProjectsModel projectsModel = new ProjectsModel();
                projectsModel.setProjectId(addProjectWrapper.getData().getId());
                projectsModel.setProjectName(addProjectWrapper.getData().getName());
                projectsModel.save();
                adapter.addItem(projectsModel);
            }
            else{
                Toast.makeText(getContext(), "There is a problem creating the project please try again.", Toast.LENGTH_SHORT).show();
            }
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
