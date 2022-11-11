package org.example.models;

public class Ticket {

    private Integer ticketId;
    private Integer employeeId;
    private Integer managerId;
    private TicketStatus status;
    private Double amount;
    private String description;
    private TicketType type;

    public Ticket() {
    }

    public Ticket(TicketType type, Integer ticketId, Integer employeeId, Integer managerId, TicketStatus status,
            Double amount,
            String description) {
        this.ticketId = ticketId;
        this.employeeId = employeeId;
        this.status = status;
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public TicketType getTicketType() {
        return type;
    }

    public void setTicketType(TicketType type) {
        this.type = type;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Ticket [type=" + type + "amount" + amount + ", description=" + description
                + ", status=" + ", ticketId=" + ticketId + "]";
    }
}
