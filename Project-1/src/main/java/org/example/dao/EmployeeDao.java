package org.example.dao;

import java.util.List;
import org.example.models.Employee;

public interface EmployeeDao {

    public void addEmployee(Employee employee) throws Exception;

    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(Integer employeeId);

    public Employee getEmployeeByEmail(String email);

    public void updateEmployee(Employee employee);;

    public void updateEmployeeAsManager(Employee employee);;

    public void deleteEmployee(String email);

}
