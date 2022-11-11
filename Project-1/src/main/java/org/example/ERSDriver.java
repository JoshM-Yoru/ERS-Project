package org.example;

import org.example.controllers.EmployeeController;
import org.example.controllers.TicketController;
import org.example.dao.EmployeeDao;
import org.example.dao.EmployeeDaoJDBC;
import org.example.dao.TicketDao;
import org.example.dao.TicketDaoJDBC;
import org.example.services.EmployeeServices;
import org.example.services.TicketServices;

import io.javalin.Javalin;

public class ERSDriver {

    public static void main(String[] args) {
        EmployeeDao eDao = new EmployeeDaoJDBC();
        EmployeeServices eServices = new EmployeeServices(eDao);
        EmployeeController eController = new EmployeeController(eServices);

        TicketDao tDao = new TicketDaoJDBC();
        TicketServices tServices = new TicketServices(tDao);
        TicketController tController = new TicketController(tServices);

        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                });
            });
        });

        app.get("/", (ctx) -> ctx.result("Welcome. Please login or register."));
        app.post("/employee/register", eController.handleRegister);
        app.post("employee/login", eController.handleLogin);
        app.get("/employee", eController.handleGetAllEmployees);
        app.post("/employee/delete", eController.handleDelete);
        app.put("/employee/update", eController.handleUpdate);
        app.get("/employee/tickets", tController.handleEmployeeTickets);

        app.post("/ticket/createTicket", tController.handleCreateTicket);
        app.get("/ticket", tController.handleGetAllTickets);
        app.get("/ticket/pending", tController.handleGetAllPendingTickets);
        app.put("/ticket/update", tController.handleUpdateTickets);
        app.post("/ticket/delete", tController.handleDeleteTicket);

        app.start(8000);

    }

}
