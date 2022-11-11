package org.example.dao;

import org.example.models.Employee;
import org.example.models.EmployeeType;
import org.example.utils.JDBCConnectionUtil;
import org.example.utils.LoggingUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoJDBC implements EmployeeDao {

    private JDBCConnectionUtil conUtil = JDBCConnectionUtil.getInstance();

    @Override
    public void addEmployee(Employee employee) throws SQLException {

        Connection connection = conUtil.getConnection();

        int type;

        if (employee.getEmployeeType() == null) {
            employee.setEmployeeType(EmployeeType.EMPLOYEE);
            type = employee.getEmployeeType().ordinal() + 1;
        } else {
            type = employee.getEmployeeType().ordinal() + 1;
        }

        String sql = "INSERT INTO employee (type, first_name, last_name, email, password) VALUES"
                + "(" + type + ",'" + employee.getFirstName() + "','" + employee.getLastName()
                + "','" + employee.getEmail() + "','" + employee.getPassword() + "')";

        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> eList = new ArrayList<>();

        try {
            Connection connection = conUtil.getConnection();

            String sql = "SELECT employeeId, type, first_name, last_name, email FROM employee WHERE active = true";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(result.getInt(1));

                if (result.getInt(2) == 1) {
                    employee.setEmployeeType(EmployeeType.MANAGER);
                } else {
                    employee.setEmployeeType(EmployeeType.EMPLOYEE);
                }

                employee.setFirstName(result.getString(3));
                employee.setLastName(result.getString(4));
                employee.setEmail(result.getString(5));

                eList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eList;
    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {

        Employee employee = null;

        try {

            Connection connection = conUtil.getConnection();

            String sql = "SELECT * FROM employee WHERE employeeId =" + employeeId;

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                employee = new Employee();
                employee.setEmployeeId(result.getInt(1));

                if (result.getInt(2) == 1) {
                    employee.setEmployeeType(EmployeeType.MANAGER);
                } else {
                    employee.setEmployeeType(EmployeeType.EMPLOYEE);
                }

                employee.setFirstName(result.getString(3));
                employee.setLastName(result.getString(4));
                employee.setEmail(result.getString(5));
                employee.setPassword(result.getString(6));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        Employee employee = null;

        try {

            Connection connection = conUtil.getConnection();

            String sql = "SELECT * FROM employee WHERE email ='" + email + "' AND active = true";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                employee = new Employee();
                employee.setEmployeeId(result.getInt(1));

                if (result.getInt(2) == 1) {
                    employee.setEmployeeType(EmployeeType.MANAGER);
                } else {
                    employee.setEmployeeType(EmployeeType.EMPLOYEE);
                }

                employee.setFirstName(result.getString(3));
                employee.setLastName(result.getString(4));
                employee.setEmail(result.getString(5));
                employee.setPassword(result.getString(6));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            LoggingUtil.getLogger().warn("Employee with email " + email + " does not exist");
        }

        return employee;
    }

    @Override
    public void updateEmployee(Employee employee) {

        try {
            Connection connection = conUtil.getConnection();

            int type = employee.getEmployeeType().ordinal() + 1;

            String sql = "UPDATE employee SET first_name = '" +
                    employee.getFirstName() +
                    "', last_name ='" + employee.getLastName() + "', email='" +
                    employee.getEmail() + "', password='" +
                    employee.getPassword() + "' WHERE employeeId ='" + employee.getEmployeeId() +
                    "'AND active = true";

            Statement statement = connection.createStatement();

            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployeeAsManager(Employee employee) {

        try {
            Connection connection = conUtil.getConnection();

            int type = employee.getEmployeeType().ordinal() + 1;

            String sql = "UPDATE employee SET type =" + type + ",first_name = '" +
                    employee.getFirstName() +
                    "', last_name ='" + employee.getLastName() + "', email='" +
                    employee.getEmail() + "', password='" +
                    employee.getPassword() + "' WHERE employeeId ='" + employee.getEmployeeId() +
                    "'AND active = true";

            Statement statement = connection.createStatement();

            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(String email) {

        try {
            Connection connection = conUtil.getConnection();

            String sql = "UPDATE employee SET active = false WHERE email='" + email + "'";

            Statement statement = connection.createStatement();

            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
