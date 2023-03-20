package view;

import controller.StaffControllerImpl;
import model.Staff;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "StaffServlet", value = "/Staff")
public class StaffServlet extends HttpServlet {
    private final StaffControllerImpl staffController = new StaffControllerImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteStaff(request,response);
                    break;
                default:
                    listStaff(request,response);
                    break;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertStaff(request, response);
                    break;
                case "edit":
                    updateStaff(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("staff/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        Staff existingStaff = staffController.showByIndex(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("staff/edit.jsp");
        request.setAttribute("staff", existingStaff);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteStaff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        staffController.delete(id);
        List<Staff> staffList = staffController.showAll();
        request.setAttribute("staffList", staffList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("staff/list.jsp");
        dispatcher.forward(request,response);
    }
    private void listStaff(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Staff> staffList = staffController.showAll();
        request.setAttribute("staffList", staffList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("staff/list.jsp");
        dispatcher.forward(request, response);
    }
    private void insertStaff(HttpServletRequest request,)


}
