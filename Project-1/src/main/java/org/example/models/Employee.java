package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

    private int employeeId;
    private EmployeeType type;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private int editorId;

    public Employee() {
        super();
    }

    public Employee(EmployeeType type, String firstName, String lastName, String email, String password,
            Integer employeeId, Integer editorId) {
        super();
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.employeeId = employeeId;
        this.editorId = editorId;
    }

    public Employee(EmployeeType type, String firstName, String lastName, String email, String password,
            Integer employeeId) {
        super();
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.employeeId = employeeId;
    }

    public Employee(EmployeeType type, String firstName, String lastName, String email, String password) {
        super();
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Employee(EmployeeType type, String firstName, String lastName, String email, Integer employeeId) {
        super();
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @JsonIgnore
    public Integer getEditorId() {
        return editorId;
    }

    @JsonProperty
    public void setEditorId(Integer editorId) {
        this.editorId = editorId;
    }

    public EmployeeType getEmployeeType() {
        return type;
    }

    public void setEmployeeType(EmployeeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Employee [type=" + type + ", firstName=" + firstName + ", lastName=" + lastName
                + ", email="
                + email + ", employeeId=" + employeeId + "]";
    }
}
