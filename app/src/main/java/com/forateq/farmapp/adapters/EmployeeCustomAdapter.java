package com.forateq.farmapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.forateq.farmapp.R;
import com.forateq.farmapp.models.EmployeesModel;

import java.util.List;

/**
 * Created by Vallejos Family on 3/7/2016.
 */
public class EmployeeCustomAdapter extends BaseAdapter {
    private Context context;
    private static LayoutInflater inflater=null;
    private List<EmployeesModel> itemsList;
    private ListView employeeListView;
    private View view;

    public EmployeeCustomAdapter(Context context, View view, List<EmployeesModel> itemsList, ListView employeeListView) {
        // TODO Auto-generated constructor stub
        this.context = context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemsList = itemsList;
        this.employeeListView = employeeListView;
        this.view = view;
    }

    /**
     * Adds item in the List of employees
     * @param employeesModel
     */
    public void addItem(EmployeesModel employeesModel){
        itemsList.add(employeesModel);
        notifyDataSetChanged();
    }

    /**
     * Removes item in the list of employees
     * @param employeesModel
     */
    public void removeItem(EmployeesModel employeesModel){
        itemsList.remove(employeesModel);
        notifyDataSetChanged();
    }

    /**
     *  Returns the size of the list
     * @return size of list
     */
    public int getSize(){
        return itemsList.size();
    }

    /**
     * Clears the list of employees
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
        TextView employee_id_tv;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.employee_item_view, null);
        holder.tv=(TextView) rowView.findViewById(R.id.employee_name);
        holder.employee_id_tv = (TextView) rowView.findViewById(R.id.employee_id);
        Log.e("Employee: ", itemsList.get(position).getEmployee_name());
        holder.tv.setText(itemsList.get(position).getEmployee_name());
        holder.employee_id_tv.setText(""+itemsList.get(position).getEmployee_id());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rowView;
    }


}
