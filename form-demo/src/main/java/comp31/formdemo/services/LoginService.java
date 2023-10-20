package comp31.formdemo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comp31.formdemo.model.Employee;
import comp31.formdemo.repositories.Accounts;

@Service
public class LoginService {

    Logger logger = LoggerFactory.getLogger(LoginService.class);

    Accounts accounts;

    public LoginService(Accounts accounts) {
        this.accounts = accounts;

        String[] userIds = { "admin", "sales", "orders" };

        for (String userId : userIds) {
            addEmployee(userId);
            // TODO add more user info
        }

    }

    public void addEmployee(String userId) {
        logger.info("Adding user: " + userId);
        Employee Employee = new Employee();
        Employee.setUserId(userId);
        accounts.add(Employee);
    }

    public void addEmployee(Employee Employee) {
        accounts.add(Employee);
    }

    public Employee findByUserId(String userId) {
        return accounts.findByUserId(userId);
    }

}
