package comp31.formdemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import comp31.formdemo.model.Employee;
import comp31.formdemo.services.LoginService;

@Controller
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    LoginService loginService;

    public MainController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    String getRoot(Model model) {
        logger.info("---- At root.");
        Employee Employee = new Employee(); // Create backing object and
        model.addAttribute("Employee", Employee); // send it to login form
        return "login-form";
    }

    @PostMapping("/login")
    public String getForm(Employee employee, Model model) {
        logger.info("---- At /login.");
        logger.info("---- " + employee.toString());
        Employee currentUser = loginService.findByUserId(employee.getUserId());
        String returnPage;

        if(employee.getUserId() == null || employee.getUserId().isEmpty() || employee.getPassword() == null || employee.getPassword().isEmpty()){
            model.addAttribute("employee", employee);
            returnPage = "login-form";
        }
        else if(employee.getUserId().equals("admin")){
            //model.addAttribute("newEmployee", new Employee());
            returnPage = "departments/admin";
        }
        //else if (currentUser.getUserId().equals(employee.getPassword()) && currentUser.getPassword().equals(employee.getPassword())) {
        else if (currentUser.getUserId().equals("ollie")) {
            model.addAttribute("employee", currentUser);
            returnPage = "departments/" + currentUser.getDepartment();
        }
        else if (currentUser == null) {
            model.addAttribute("employee", employee);
            returnPage = "login-form";
        } 
        else {
            model.addAttribute("employee", currentUser);
            returnPage = "welcome";
        }
        return returnPage;
    }

    @GetMapping("/add-employee")
    public String getAddEmployee(Model model) {
        logger.info("---- At /add-employee.");
        model.addAttribute("newEmployee", new Employee());
        return "new-employee-form";
    }

    @PostMapping("/add-employee")
    public String postAddEmployee(Model model, Employee employee) {
        
        loginService.addEmployee(employee);
        model.addAttribute("employee", new Employee());
        return "login-form";
    }

}
