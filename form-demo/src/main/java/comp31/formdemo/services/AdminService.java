package comp31.formdemo.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import comp31.formdemo.model.Employee;
import comp31.formdemo.repositories.Accounts;

// @Service annotation indicates that this class is a Spring service. It contains business logic 
// and is used to interact with the underlying data repositories and other related services.
@Service
public class AdminService {

    // Dependency on the Accounts component which presumably is responsible for CRUD operations on Employee entities
    Accounts accounts;

    // Constructor-based dependency injection of the Accounts component
    public AdminService(Accounts accounts) {
        this.accounts = accounts;
    }

    // Method to retrieve all the employees
    public ArrayList<Employee> findAllEmployees() {
        return accounts.findAllEmployees(); // delegate the call to the 'accounts' component to fetch all employees
    }

    // Method to retrieve employees belonging to a specific department
    public ArrayList<Employee> findByDepartment(String department) {
        return accounts.findByDepartment(department); // delegate the call to the 'accounts' component to fetch employees by department
    }
}

