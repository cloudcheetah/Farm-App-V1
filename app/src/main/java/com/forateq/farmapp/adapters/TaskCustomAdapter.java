package com.forateq.farmapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.forateq.farmapp.R;
import com.forateq.farmapp.models.TaskModel;

import java.util.List;

/**
 * Created by Vallejos Family on 3/1/2016.
 */
public class TaskCustomAdapter extends BaseAdapter {
    private Context context;
    private static LayoutInflater inflater=null;
    private List<TaskModel> itemsList;
    private View view;

    public TaskCustomAdapter(Context context, View view, List<TaskModel> itemsList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemsList = itemsList;
        this.view = view;
    }

    /**
     * Adds item in the list of tasks
     * @param taskModel
     */
    public void addItem(TaskModel taskModel){
        itemsList.add(taskModel);
        notifyDataSetChanged();
    }

    /**
     * Removes a task in the list of tasks
     * @param taskModel
     */
    public void removeItem(TaskModel taskModel){
        itemsList.remove(taskModel);
        notifyDataSetChanged();
    }

    /**
     * Returns the size of the list of tasks
     * @return int size
     */
    public int getSize(){
        return itemsList.size();
    }

    /**
     * clears the items in the list of tasks
     */
    public void clearItems(){
        itemsList.clear();
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
        TextView task_id_tv;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.project_item_view, null);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.tv = (TextView) rowView.findViewById(R.id.project_name);
        holder.task_id_tv = (TextView) rowView.findViewById(R.id.task_id);
        holder.project_id_tv = (TextView) rowView.findViewById(R.id.project_id);
        holder.tv.setText(itemsList.get(position).getTask_name());
        holder.task_id_tv.setText(""+itemsList.get(position).getTask_id());
        holder.project_id_tv.setText(""+itemsList.get(position).getProject_id());
        return rowView;
    }

}
