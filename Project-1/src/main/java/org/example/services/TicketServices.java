package org.example.services;

import java.util.ArrayList;
import java.util.List;

import org.example.dao.EmployeeDao;
import org.example.dao.EmployeeDaoJDBC;
import org.example.dao.TicketDao;
import org.example.models.Employee;
import org.example.models.EmployeeType;
import org.example.models.Ticket;
import org.example.utils.LoggingUtil;

public class TicketServices {

    private TicketDao ticketDao;
    private EmployeeDao employeeDao = new EmployeeDaoJDBC();

    public TicketServices(TicketDao ticketDao, EmployeeDao employeeDao) {
        this.ticketDao = ticketDao;
        this.employeeDao = employeeDao;
    }

    public TicketServices(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public boolean createTicket(Ticket ticket) {
        try {
            if (ticket.getEmployeeId() != null && ticket.getAmount() != null && ticket.getDescription() != null
                    && ticket.getTicketType() != null) {
                ticketDao.createTicket(ticket);
                LoggingUtil.getLogger().info("New Ticket Submitted.");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTicket(Ticket ticket) {
        try {
            List<Employee> eList = new ArrayList<>();

            eList = employeeDao.getAllEmployees();

            for (int i = 0; i < eList.size(); i++) {
                if (eList.get(i).getEmployeeId().equals(ticket.getManagerId())
                        && eList.get(i).getEmployeeType().equals(EmployeeType.MANAGER)
                        && ticket.getEmployeeId() != ticket.getManagerId()) {
                    ticketDao.updateTicket(ticket);
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LoggingUtil.getLogger().warn("Invalid Privledges.");
        }
        return false;
    }

    public List<Ticket> getAllTickets() throws Exception {
        return ticketDao.getAllTickets();
    }

    public List<Ticket> getAllPendingTickets(Ticket ticket) throws Exception {
        List<Employee> eList = new ArrayList<>();

        eList = employeeDao.getAllEmployees();

        for (int i = 0; i < eList.size(); i++) {
            if (eList.get(i).getEmployeeId().equals(ticket.getEmployeeId())
                    && eList.get(i).getEmployeeType().equals(EmployeeType.MANAGER)) {
                break;
            } else {
                throw new Exception();
            }
        }
        return ticketDao.getAllPendingTickets();
    }

    public List<Ticket> getEmployeeTickets(Ticket ticket) {
        return ticketDao.getEmployeeTickets(ticket);
    }

    public boolean deleteTicket(Ticket ticket) {
        try {

            List<Ticket> tList = new ArrayList<>();

            tList = ticketDao.getAllPendingTickets();

            for (int i = 0; i < tList.size(); i++) {
                if (tList.get(i).getEmployeeId().equals(ticket.getEmployeeId())) {
                    ticketDao.deleteTicket(ticket);
                    LoggingUtil.getLogger().info("Pending ticket was withdrawn.");
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
