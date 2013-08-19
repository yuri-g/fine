/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import ejb.UsersEntity;
import ejb.UsersEntityFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Password;

/**
 *
 * @author yuri
 */
@WebServlet(name = "CreateSession", urlPatterns = {"/log_in"})
public class CreateSession extends HttpServlet {
    
    
    @EJB
    private UsersEntityFacade usersEntityFacade;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
    }

 
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = getServletContext().getRequestDispatcher("/user/login.jsp");
        view.forward(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ePassword = request.getParameter("password");
        String email = request.getParameter("email");
        UsersEntity user = usersEntityFacade.findByEmail(email);
        try {
            log("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            log(user.getPassword());
            log(ePassword);
            if (!Password.check(ePassword, user.getPassword())) {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/user/login.jsp");
                request.setAttribute("errors", "Something went wrong!");
                view.forward(request, response);
            }
            else {
                response.sendRedirect("/");
            }
        } catch (Exception ex) {
            Logger.getLogger(CreateSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
