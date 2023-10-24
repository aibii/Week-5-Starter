package comp31.formdemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import comp31.formdemo.model.Employee;
import comp31.formdemo.services.AdminService;
import comp31.formdemo.services.LoginService;

// @Controller annotation indicates that this class serves as a Spring MVC controller
@Controller
// MainController class is responsible for handling incoming HTTP requests and returning appropriate views (HTML pages)
public class MainController {

    // Logger instance to log messages and events
    Logger logger = LoggerFactory.getLogger(MainController.class);

    // Service instance for login-related functionalities
    LoginService loginService;
    // Service instance for admin-related functionalities
    AdminService adminService;

    // Constructor-based dependency injection of services
    public MainController(LoginService loginService, AdminService adminService) {
        this.loginService = loginService;
        this.adminService = adminService;
    }

    // Handles GET requests to the root ("/") URL
    @GetMapping("/")
    String getRoot(Model model) {
        logger.info("---- At root.");
        Employee Employee = new Employee(); // Create a new Employee instance
        model.addAttribute("employee", Employee); // Attach the new employee to the model for the view
        return "login-form"; // Return the login form view
    }

    // Handles POST requests to the "/login" URL
    @PostMapping("/login")
    public String getForm(Employee employee, Model model) {
        logger.info("---- At /login.");
        logger.info("---- " + employee.toString());
        Employee currentUser = loginService.findByUserId(employee.getUserId()); // Find the user by their user ID

        String returnPage = loginService.validateUserInput(employee, currentUser, model); // Validate the user input

        // Check if the user is an admin
        if(employee.getUserId().equals("admin") && employee.getPassword().equals("admin")) {
            returnPage = "redirect:/findAllEmployees"; // Redirect to the list of all employees for admin
        }
        return returnPage; // Return the appropriate page based on the validation
    }

    // Handles GET requests to the "/add-employee" URL
    @GetMapping("/add-employee")
    public String getAddEmployee(Model model) {
        logger.info("---- At /add-employee.");
        model.addAttribute("newEmployee", new Employee()); // Add a new employee instance to the model for the view
        return "new-employee-form"; // Return the form to add a new employee
    }

    // Handles POST requests to add a new employee
    @PostMapping("/add-employee")
    public String postAddEmployee(Model model, Employee employee) {
        loginService.addEmployee(employee); // Add the new employee to the database/service
        model.addAttribute("employee", new Employee()); // Reset the employee instance in the model
        return "login-form"; // Redirect back to the login form
    }

    // Handles GET requests to list all employees
    @GetMapping("/findAllEmployees")
    public String getFindAllEmployees(Model model) {
        logger.info("---- At /findAllEmployees.");
        model.addAttribute("employees", adminService.findAllEmployees()); // Attach the list of all employees to the model
        model.addAttribute("headerTitle", "All Employees"); // Set the header title for the view
        return "departments/admin"; // Return the admin view that lists all employees
    }

    // Handles GET requests to list employees by department
    @GetMapping("/findByDepartment")
    public String getFindByDepartment(@RequestParam("userDepartment") String userDepartment, Model model)  {
        String capitalizedDepartment = userDepartment.substring(0, 1).toUpperCase() + userDepartment.substring(1); // Capitalize the first letter of the department name
        logger.info("---- At /findByDepartment.");
        model.addAttribute("employees", adminService.findByDepartment(userDepartment)); // Attach the list of employees from a specific department to the model
        model.addAttribute("headerTitle", capitalizedDepartment + " Department"); // Set the header title for the view
        return "departments/admin"; // Return the admin view that lists employees by department
    }

}
