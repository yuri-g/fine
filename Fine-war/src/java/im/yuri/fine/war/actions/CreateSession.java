/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.war.actions;

import im.yuri.fine.ejb.entities.SessionEntity;
import im.yuri.fine.ejb.entities.UsersEntity;
import im.yuri.fine.ejb.entities.facades.SessionEntityFacade;
import im.yuri.fine.ejb.entities.facades.UsersEntityFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import im.yuri.fine.war.util.Password;

/**
 *
 * @author yuri
 */
@WebServlet(name = "CreateSession", urlPatterns = {"/log_in"})
public class CreateSession extends HttpServlet {
    @EJB
    private SessionEntityFacade sessionEntityFacade;
    
    
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
            if (!Password.check(ePassword, user.getPassword())) {
                RequestDispatcher view = getServletContext().getRequestDispatcher("/user/login.jsp");
                request.setAttribute("errors", "Something went wrong!");
                view.forward(request, response);
            }
            else {
                SessionEntity e = sessionEntityFacade.findByUserEmail(email);
                if (e != null) {
                    sessionEntityFacade.remove(e);    
                }
                e = new SessionEntity();
                e.setUser(user);
                e.setHash(request.getSession().getId());
                sessionEntityFacade.create(e);
                response.sendRedirect("/ListUsers");
            }
        } 
        catch (Exception ex) {
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
