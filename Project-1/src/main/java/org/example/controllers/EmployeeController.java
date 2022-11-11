package org.example.controllers;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import org.example.exceptions.EmployeeDoesNotExistException;
import org.example.models.Employee;
import org.example.services.EmployeeServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;

public class EmployeeController {

    private EmployeeServices employeeServices;

    private ObjectMapper objectMapper;

    public EmployeeController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
        objectMapper = new ObjectMapper();
    }

    public Handler handleRegister = (ctx) -> {
        try {
            Employee employee = objectMapper.readValue(ctx.body(), Employee.class);

            if (employeeServices.registerEmployee(employee) == true) {
                ctx.status(409);
                ctx.result("Email is already in use. Register with a different email address.");
            } else {
                ctx.status(201);
                ctx.result("Registration Succesful.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public Handler handleLogin = (ctx) -> {
        Map<String, String> body = objectMapper.readValue(ctx.body(), LinkedHashMap.class);
        Employee loggedInEmployee = employeeServices.login(body.get("email"), body.get("password"));
        try {
            if (loggedInEmployee != null) {
                ctx.status(200);
                ctx.result("Login Successful.");
            } else {
                ctx.status(401);
                ctx.result("Email or password was incorrect. Please try again.");
            }
        } catch (EmployeeDoesNotExistException e) {
            e.printStackTrace();
        }
    };

    public Handler handleGetAllEmployees = (ctx) -> {
        List<Employee> employees = employeeServices.getAllEmployees();
        ctx.status(200);
        ctx.result(objectMapper.writeValueAsString(employees));
    };
    public Handler handleDelete = (ctx) -> {
        Map<String, String> body = objectMapper.readValue(ctx.body(), LinkedHashMap.class);

        employeeServices.removeEmployee(body.get("email"));

        ctx.status(200);
        ctx.result("Employee was removed");
    };

    public Handler handleUpdate = (ctx) -> {
        Employee employee = objectMapper.readValue(ctx.body(), Employee.class);

        if (employeeServices.updateEmployee(employee) == false) {
            ctx.status(403);
            ctx.result("You are unable to make these changes.");
        } else {
            ctx.status(200);
            ctx.result("Employees information was updated.");
        }
    };

}
