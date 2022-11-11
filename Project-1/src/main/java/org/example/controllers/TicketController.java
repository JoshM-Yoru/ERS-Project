package org.example.controllers;

import java.util.List;

import org.example.models.Ticket;
import org.example.services.TicketServices;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.http.Handler;

public class TicketController {

    private TicketServices ticketServices;

    private ObjectMapper objectMapper;

    public TicketController(TicketServices ticketServices) {
        this.ticketServices = ticketServices;
        objectMapper = new ObjectMapper();
    }

    public Handler handleCreateTicket = (ctx) -> {
        try {
            Ticket ticket = objectMapper.readValue(ctx.body(), Ticket.class);

            if (ticketServices.createTicket(ticket) == false) {
                ctx.status(400);
                ctx.result("All fields must be completed.");
            } else {
                ctx.status(201);
                ctx.result(objectMapper.writeValueAsString(ticket));
                ctx.result("Ticket Submitted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public Handler handleGetAllTickets = (ctx) -> {
        List<Ticket> tickets = ticketServices.getAllTickets();
        ctx.status(200);
        ctx.result("All Tickets Are Being Displayed: ");
        ctx.result(objectMapper.writeValueAsString(tickets));
    };

    public Handler handleGetAllPendingTickets = (ctx) -> {
        Ticket ticket = objectMapper.readValue(ctx.body(), Ticket.class);
        List<Ticket> tickets = ticketServices.getAllPendingTickets(ticket);
        ctx.status(200);
        ctx.result("Here are the pending tickets that need to be processed: ");
        ctx.result(objectMapper.writeValueAsString(tickets));
    };

    public Handler handleEmployeeTickets = (ctx) -> {
        Ticket ticket = objectMapper.readValue(ctx.body(), Ticket.class);
        List<Ticket> tickets = ticketServices.getEmployeeTickets(ticket);
        ctx.status(200);
        ctx.result("Here are your submitted tickets: ");
        ctx.result(objectMapper.writeValueAsString(tickets));
    };

    public Handler handleUpdateTickets = (ctx) -> {
        Ticket ticket = objectMapper.readValue(ctx.body(), Ticket.class);

        if (ticketServices.updateTicket(ticket) == false) {
            ctx.status(401);
            ctx.result("You are not authorized to approve or deny this ticket.");
        } else {
            ctx.status(202);
            ctx.result("Ticket has been updated.");
        }
    };

    public Handler handleDeleteTicket = (ctx) -> {
        Ticket ticket = objectMapper.readValue(ctx.body(), Ticket.class);
        if (ticketServices.deleteTicket(ticket) == false) {
            ctx.status(403);
            ctx.result("You can not withdraw another employee's ticket.");
        }
        ctx.status(202);
        ctx.result("Ticket withdrawn.");
    };
}
