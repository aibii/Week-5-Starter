package comp31.formdemo.repositories;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import comp31.formdemo.model.Employee;

@Component
public class Accounts extends ArrayList<Employee> {

    public Employee findByUserId(String userId) {
        for (Employee Employee : this) {
            if (Employee.getUserId().equals(userId))
                return Employee;
        }
        return null;
    }

    // TODO add findByDepartment

    public ArrayList<Employee> findAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<Employee>(); //creating a new arrayList object
        for (Employee employee : this) { //adding all Employees to the new arrayList
            employees.add(employee);
        }
        return employees;
    }

    public ArrayList<Employee> findByDepartment(String department) {
        ArrayList<Employee> employees = new ArrayList<Employee>(); //creating a new arrayList object
        for (Employee employee : this) { //adding all Employees to the new arrayList
            if (employee.getDepartment().equals(department)) {
                employees.add(employee);
            }
        }
        return employees;
    }

}
