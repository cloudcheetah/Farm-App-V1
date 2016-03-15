package com.forateq.farmapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Vallejos Family on 3/7/2016.
 */
@Table(name = "Employees")
public class EmployeesModel extends Model {

    @Column(name = "employee_id")
    private int employee_id;

    @Column(name = "employee_name")
    private String employee_name;

    /**
     * use to get the employee_id
     * @return employee_id
     */
    public int getEmployee_id() {
        return employee_id;
    }

    /**
     * sets the employee_id
     * @param employee_id
     */
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    /**
     * returns the employee_name
     * @return employee_name
     */
    public String getEmployee_name() {
        return employee_name;
    }

    /**
     * sets the employee_name
     * @param employee_name
     */
    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    /**
     * returns all the employee from the sqlite
     * @return list of employees
     */
    public static List<EmployeesModel> getAllEmployees(){
        return new Select().from(EmployeesModel.class).execute();
    }
}
