package org.example.services;

import java.util.ArrayList;
import java.util.List;

import org.example.dao.EmployeeDao;
import org.example.exceptions.EmployeeDoesNotExistException;
import org.example.models.Employee;
import org.example.models.EmployeeType;
import org.example.utils.LoggingUtil;

public class EmployeeServices {

    private EmployeeDao employeeDao;

    public EmployeeServices(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public boolean registerEmployee(Employee employee) {
        try {
            List<Employee> eList = new ArrayList<>();

            eList = employeeDao.getAllEmployees();

            for (int i = 0; i < eList.size(); i++) {
                if (eList.get(i).getEmail().equals(employee.getEmail())) {
                    return true;
                }
            }
            employeeDao.addEmployee(employee);
            LoggingUtil.getLogger().info("New Employee Registered");
        } catch (Exception e) {
            LoggingUtil.getLogger().warn(employee.getEmail() + " has already been used to register.");
        }
        return false;
    }

    public Employee login(String email, String password) {
        Employee employee = employeeDao.getEmployeeByEmail(email);
        if (employee != null && employee.getPassword().equals(password)) {
            if (!employee.getPassword().equals(password)) {
                return null;
            }
            LoggingUtil.getLogger().info("Employee logged in");
            return employee;
        } else {
            LoggingUtil.getLogger().warn("Username or password was incorrect.");
            return null;
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    public void removeEmployee(String email) {
        employeeDao.deleteEmployee(email);
        LoggingUtil.getLogger().info("User " + email + " was removed from the system");
    }

    public boolean updateEmployee(Employee employee) {

        Employee dbEmployee = employeeDao.getEmployeeById(employee.getEmployeeId());
        List<Employee> eList = new ArrayList<>();

        eList = employeeDao.getAllEmployees();

        if (employee.getEmployeeType() == null) {
            employee.setEmployeeType(dbEmployee.getEmployeeType());
        }
        if (employee.getFirstName() == null) {
            employee.setFirstName(dbEmployee.getFirstName());
        }
        if (employee.getLastName() == null) {
            employee.setLastName(dbEmployee.getLastName());
        }
        if (employee.getEmail() == null) {
            employee.setEmail(dbEmployee.getEmail());
        }
        if (employee.getPassword() == null) {
            employee.setPassword(dbEmployee.getPassword());
        }
        if (employee.getEmployeeId() == null) {
            throw new EmployeeDoesNotExistException();
        }

        for (int i = 0; i < eList.size(); i++) {
            if (employee.getEmployeeId() == employee.getEditorId()) {
                if (employee.getEmployeeType().equals(EmployeeType.MANAGER)) {
                    return false;
                }
                employeeDao.updateEmployee(employee);
                return true;
            } else if (eList.get(i).getEmployeeId().equals(employee.getEditorId())
                    && eList.get(i).getEmployeeType().equals(EmployeeType.MANAGER)) {
                employeeDao.updateEmployeeAsManager(employee);
                return true;
            }
        }
        LoggingUtil.getLogger().info("User " + employee.getEmployeeId() + " was successfully updated in the system");
        return false;
    }

}
