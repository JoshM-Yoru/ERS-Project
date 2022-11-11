package org.example.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.models.Ticket;
import org.example.models.TicketStatus;
import org.example.models.TicketType;
import org.example.utils.JDBCConnectionUtil;
import org.example.utils.LoggingUtil;

public class TicketDaoJDBC implements TicketDao {

    private JDBCConnectionUtil conUtil = JDBCConnectionUtil.getInstance();

    @Override
    public void createTicket(Ticket ticket) throws SQLException {

        Connection connection = conUtil.getConnection();
        ticket.setStatus(TicketStatus.PENDING);
        int status = ticket.getStatus().ordinal() + 1;
        int type = ticket.getTicketType().ordinal() + 1;

        String sql = "INSERT INTO ticket (type, amount, description, status, employeeId) VALUES"
                + "(" + type + ",'" + ticket.getAmount() + "','" + ticket.getDescription()
                + "','" + status + "','" + ticket.getEmployeeId() + "')";

        Statement statement = connection.createStatement();
        statement.execute(sql);

    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tList = new ArrayList<>();

        try {
            Connection connection = conUtil.getConnection();

            String sql = "SELECT * FROM ticket";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Ticket ticket = new Ticket();

                if (result.getInt(2) == 1) {
                    ticket.setTicketType(TicketType.FOOD);
                } else if (result.getInt(2) == 2) {
                    ticket.setTicketType(TicketType.LODGING);
                } else if (result.getInt(2) == 3) {
                    ticket.setTicketType(TicketType.TRAVEL);
                } else {
                    ticket.setTicketType(TicketType.MISCELLANEOUS);
                }

                if (result.getInt(5) == 1) {
                    ticket.setStatus(TicketStatus.APPROVED);
                } else if (result.getInt(5) == 2) {
                    ticket.setStatus(TicketStatus.DENIED);
                } else if (result.getInt(5) == 3) {
                    ticket.setStatus(TicketStatus.PENDING);
                }

                ticket.setTicketId(result.getInt(1));
                ticket.setAmount(new Double(result.getString(3)));
                ticket.setDescription(result.getString(4));
                ticket.setManagerId(result.getInt(6));
                ticket.setEmployeeId(result.getInt(7));

                tList.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tList;
    }

    @Override
    public List<Ticket> getAllPendingTickets() {
        List<Ticket> tList = new ArrayList<>();

        try {
            Connection connection = conUtil.getConnection();

            String sql = "SELECT * FROM ticket WHERE status = '3'";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Ticket ticket = new Ticket();

                if (result.getInt(2) == 1) {
                    ticket.setTicketType(TicketType.FOOD);
                } else if (result.getInt(2) == 2) {
                    ticket.setTicketType(TicketType.LODGING);
                } else if (result.getInt(2) == 3) {
                    ticket.setTicketType(TicketType.TRAVEL);
                } else {
                    ticket.setTicketType(TicketType.MISCELLANEOUS);
                }

                if (result.getInt(5) == 1) {
                    ticket.setStatus(TicketStatus.APPROVED);
                } else if (result.getInt(5) == 2) {
                    ticket.setStatus(TicketStatus.DENIED);
                } else if (result.getInt(5) == 3) {
                    ticket.setStatus(TicketStatus.PENDING);
                }

                ticket.setTicketId(result.getInt(1));
                ticket.setAmount(new Double(result.getString(3)));
                ticket.setDescription(result.getString(4));
                ticket.setManagerId(result.getInt(6));
                ticket.setEmployeeId(result.getInt(7));

                tList.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tList;
    }

    @Override
    public List<Ticket> getEmployeeTickets(Ticket ticket) {
        List<Ticket> tList = new ArrayList<>();

        try {
            Connection connection = conUtil.getConnection();

            String sql = "SELECT * FROM ticket WHERE employeeId = '" + ticket.getEmployeeId() + "'";

            if (ticket.getStatus() != null) {
                int status = ticket.getStatus().ordinal() + 1;
                sql = "SELECT * FROM ticket WHERE employeeId = '" + ticket.getEmployeeId() + "' AND status = '"
                        + status + "'";
            }

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                if (result.getInt(2) == 1) {
                    ticket.setTicketType(TicketType.FOOD);
                } else if (result.getInt(2) == 2) {
                    ticket.setTicketType(TicketType.LODGING);
                } else if (result.getInt(2) == 3) {
                    ticket.setTicketType(TicketType.TRAVEL);
                } else {
                    ticket.setTicketType(TicketType.MISCELLANEOUS);
                }

                if (result.getInt(5) == 1) {
                    ticket.setStatus(TicketStatus.APPROVED);
                } else if (result.getInt(5) == 2) {
                    ticket.setStatus(TicketStatus.DENIED);
                } else if (result.getInt(5) == 3) {
                    ticket.setStatus(TicketStatus.PENDING);
                }

                ticket.setTicketId(result.getInt(1));
                ticket.setAmount(new Double(result.getString(3)));
                ticket.setDescription(result.getString(4));
                ticket.setManagerId(result.getInt(6));
                ticket.setEmployeeId(result.getInt(7));

                tList.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tList;
    }

    @Override
    public void updateTicket(Ticket ticket) {

        try {
            Connection connection = conUtil.getConnection();

            int status = ticket.getStatus().ordinal() + 1;

            String sql = "UPDATE ticket SET status ='" + status + "', managerId = '" + ticket.getManagerId()
                    + "' WHERE status = '3' AND ticketId ='"
                    + ticket.getTicketId() + "'";

            Statement statement = connection.createStatement();

            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            LoggingUtil.getLogger().warn("You can not modify an already processed ticket.");
        }

    }

    @Override
    public void deleteTicket(Ticket ticket) {
        try {
            Connection connection = conUtil.getConnection();

            String sql = "DELETE FROM ticket WHERE ticketId = '" + ticket.getTicketId() + "' AND status = '3'";

            Statement statement = connection.createStatement();

            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
