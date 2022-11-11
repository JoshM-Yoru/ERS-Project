package org.example.dao;

import java.sql.SQLException;
import java.util.List;

import org.example.models.Ticket;

public interface TicketDao {

    public void createTicket(Ticket ticket) throws SQLException;

    public List<Ticket> getAllTickets();

    public List<Ticket> getAllPendingTickets();

    public List<Ticket> getEmployeeTickets(Ticket ticket);

    public void updateTicket(Ticket ticket) throws Exception;

    public void deleteTicket(Ticket ticket);
}
