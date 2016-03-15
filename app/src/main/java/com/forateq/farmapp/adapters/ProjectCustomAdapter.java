package com.forateq.farmapp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.forateq.farmapp.FarmAppService;
import com.forateq.farmapp.FarmApplication;
import com.forateq.farmapp.R;
import com.forateq.farmapp.fragments.ERPFragment;
import com.forateq.farmapp.models.ProjectsModel;
import com.forateq.farmapp.models.SessionKey;
import com.forateq.farmapp.models.TaskModel;
import com.forateq.farmapp.objects.AddProjectWrapper;
import com.forateq.farmapp.objects.TasksWrapper;
import com.forateq.farmapp.views.ProjectDetailsView;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by Vallejos Family on 2/25/2016.
 */
public class ProjectCustomAdapter extends BaseAdapter {
    private Context context;
    private static LayoutInflater inflater=null;
    private List<ProjectsModel> itemsList;
    private ListView projectListView;
    private View view;

    public ProjectCustomAdapter(Context context, View view, List<ProjectsModel> itemsList, ListView projectListView) {
        // TODO Auto-generated constructor stub
        this.context = context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemsList = itemsList;
        this.projectListView = projectListView;
        this.view = view;
    }

    /**
     * Adds project on the list of projects
     * @param projectsModel
     */
    public void addItem(ProjectsModel projectsModel){
        itemsList.add(projectsModel);
        notifyDataSetChanged();
    }

    /**
     * Removes a project in the list of projects
     * @param projectsModel
     */
    public void removeItem(ProjectsModel projectsModel){
        Log.e("Name: ", ""+projectsModel.getProjectName());
        itemsList.remove(projectsModel);
        notifyDataSetChanged();
    }

    /**
     * Returns the size of the list of projects
     * @return size of list
     */
    public int getSize(){
        return itemsList.size();
    }

    /**
     * Clears all the items in the list of projects
     */
    public void clearItems(){
        itemsList.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView project_id_tv;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.project_item_view, null);
        holder.tv=(TextView) rowView.findViewById(R.id.project_name);
        holder.project_id_tv = (TextView) rowView.findViewById(R.id.project_id);
        holder.tv.setText(itemsList.get(position).getProjectName());
        holder.project_id_tv.setText(""+itemsList.get(position).getProjectId());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetProjectTask(Integer.parseInt(holder.project_id_tv.getText().toString()), holder.tv.getText().toString(), itemsList.get(position), holder.tv).execute();
            }
        });
        return rowView;
    }

    /**
     * Task that gets all the list of projects in the API
     */
    class GetProjectTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        private AddProjectWrapper addProjectWrapper;
        private int projectId;
        private String projectName;
        private ProjectsModel projectsModel;
        private TextView projectNameTextView;

        public GetProjectTask(int projectId, String projectName, ProjectsModel projectsModel, TextView projectNameTextView) {
            this.projectId = projectId;
            this.projectName = projectName;
            this.projectsModel = projectsModel;
            this.projectNameTextView = projectNameTextView;
        }

        @Override
        protected Void doInBackground(Void... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(FarmAppService.URL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            FarmAppService service = restAdapter.create(FarmAppService.class);
            addProjectWrapper = service.getProjectById(projectId, SessionKey.getSessionKeyValue().getUser(), FarmApplication.DEVICE_ID, SessionKey.getSessionKeyValue().getSession_key());
            new Delete().from(TaskModel.class).execute();
            for(TasksWrapper tasksWrapper : addProjectWrapper.getTasks()){
                TaskModel taskModel = new TaskModel();
                taskModel.setTask_id(tasksWrapper.getId());
                taskModel.setTask_name(tasksWrapper.getName());
                taskModel.setProject_id(tasksWrapper.getProject_id());
                taskModel.save();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Getting project details");
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(addProjectWrapper.getResponse().getResponse_text().equals("Operation Successful.")){
                ProjectDetailsView projectDetailsView = new ProjectDetailsView(context, projectName, TaskModel.getTasks(), ProjectCustomAdapter.this, projectsModel, projectListView, projectNameTextView);
                projectDetailsView.getProjectNameEditText().setText(addProjectWrapper.getData().getName());
                projectDetailsView.getProjectStartDateEditText().setText(addProjectWrapper.getData().getStart_date());
                projectDetailsView.getProjectEndDateEditText().setText(addProjectWrapper.getData().getEnd_date());
                projectDetailsView.getProjectBudgetEditText().setText(""+addProjectWrapper.getData().getBudget());
                projectDetailsView.getProjectDescriptionEditText().setText(addProjectWrapper.getData().getDescription());
                projectDetailsView.getProjectIdTextView().setText(""+addProjectWrapper.getData().getId());
                projectDetailsView.getLabelTextView().setText(addProjectWrapper.getData().getName());
                FarmApplication.viewDeque.addLast(view);
                ERPFragment.changeView(projectDetailsView);
            }
            else{
                Toast.makeText(context, "There is a problem retrieving the project details please try again.", Toast.LENGTH_SHORT).show();
            }
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }

}
