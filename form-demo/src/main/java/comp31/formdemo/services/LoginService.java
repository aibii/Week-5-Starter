package comp31.formdemo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import comp31.formdemo.model.Employee;
import comp31.formdemo.repositories.Accounts;

// @Service annotation indicates that this class is a Spring service component. This class specifically contains logic 
// related to logging in users and validating their credentials.
@Service
public class LoginService {

    // Logger instance to capture and log relevant information during the execution
    Logger logger = LoggerFactory.getLogger(LoginService.class);

    // Dependency on the Accounts component which presumably handles CRUD operations for Employee entities
    Accounts accounts;

    // Constructor for this service. Responsible for dependency injection of the Accounts component and also
    // for initializing some hardcoded employees.
    public LoginService(Accounts accounts) {
        this.accounts = accounts;

        // The following block initializes a set of hardcoded employees on object creation.
        String[] userIds = {
            "sam", "sally", "ollie", "olivia", "rachel", "ralph", "abbie", "arthur"
        };
        String[] departments = {
            "sales", "sales", "orders", "orders", "repairs", "repairs", "admin", "admin"
        };
        String[] firstNames = {
            "Sam", "Sally", "Ollie", "Olivia", "Rachel", "Ralph", "Abbie", "Arthur"
        };
        String[] lastNames = {
            "Smith", "Johnson", "Brown", "Davis", "White", "Jones", "Wilson", "Miller"
        };

        for (int i = 0; i < userIds.length; i++) {
            String userId = userIds[i];
            String department = departments[i];
            String firstName = firstNames[i];
            String lastName = lastNames[i];

            // Using the addEmployee method to add each hardcoded employee to the system.
            addEmployee(userId, department, firstName, lastName);
        }
    }

    // Method to add a new employee to the system given specific parameters.
    // This includes logging details about the added user.
    public void addEmployee(String userId, String department, String firstName, String lastName) {
        logger.info("Adding user: " + userId);
        logger.info("with department: " + department);
        logger.info("with first name: " + firstName);
        Employee employee = new Employee();
        employee.setUserId(userId);
        employee.setDepartment(department);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        // Setting the password to be the same as the userId for simplicity. 
        employee.setPassword(userId);

        // Delegate the task of adding the employee to the accounts component.
        accounts.add(employee);
    }

    // Method to add a new employee to the system given an Employee object.
    public void addEmployee(Employee Employee) {
        accounts.add(Employee); // delegate to the accounts component to add the employee
    }

    // Method to retrieve an Employee based on their userId.
    public Employee findByUserId(String userId) {
        return accounts.findByUserId(userId); // delegate to the accounts component to find by userId
    }

    // This method is responsible for validating user inputs during the login process and determining
    // which page to navigate to based on the validation results.
    public String validateUserInput(Employee employee, Employee currentUser, Model model) {
        String returnPage;

        // Check for missing credentials.
        if(employee.getUserId() == null || employee.getUserId().isEmpty() || 
           employee.getPassword() == null || employee.getPassword().isEmpty()) {
            model.addAttribute("employee", employee);
            returnPage = "login-form";  // If credentials are missing, redirect back to login form.
        }
        // Check if the user trying to login is the admin.
        else if(employee.getUserId().equals("admin")) {
            returnPage = "departments/admin";  // Admin is redirected to the admin department page.
        }
        // Special case for user 'ollie'.
        else if (currentUser.getUserId().equals("ollie")) {
            model.addAttribute("employee", currentUser);
            returnPage = "departments/" + currentUser.getDepartment(); // 'ollie' is redirected to their department page.
        }
        // Check if the user was not found in the system.
        else if (currentUser == null) {
            model.addAttribute("employee", employee);
            returnPage = "login-form";  // If user doesn't exist, redirect back to login form.
        } 
        else {
            model.addAttribute("employee", currentUser);
            returnPage = "welcome";  // For other users, redirect to the welcome page.
        }
        return returnPage;
    }
}
