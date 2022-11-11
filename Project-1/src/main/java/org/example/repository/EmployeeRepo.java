package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.models.Employee;
import org.example.models.EmployeeType;
import org.example.utils.JDBCConnectionUtil;

public class EmployeeRepo {

    static JDBCConnectionUtil util = JDBCConnectionUtil.getInstance();

    public Employee getEmployeeFromDB(String email) {
        String sql = "SELECT * FROM employee WHERE email = ?";
        try (Connection connection = util.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email.toLowerCase());
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return new Employee(
                        EmployeeType.valueOf(result.getString("employeeType")),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
