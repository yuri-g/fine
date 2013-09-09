/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.war.actions;

import im.yuri.fine.ejb.UserSessionBeanRemote;
import im.yuri.fine.ejb.entities.UsersEntity;
import im.yuri.fine.ejb.entities.facades.UsersEntityFacade;
import im.yuri.fine.war.util.Password;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin
 */
@WebServlet(name = "UpdateUser", urlPatterns = {"/settings"})
public class UpdateUser extends HttpServlet {
    
    
    
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
        RequestDispatcher view = request.getRequestDispatcher("/session/edit.jsp");
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
        InitialContext context;
        UserSessionBeanRemote userSessionBean;
        try {
            context = new InitialContext();
            userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
        } catch (NamingException ex) {
            Logger.getLogger(CreateBlogEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        userSessionBean = (UserSessionBeanRemote)request.getSession().getAttribute("uSessionBean");
        if(userSessionBean != null) {
            String errors = "";
            String password = request.getParameter("password");
            String newPassword = request.getParameter("nPassword");
            String cPassword = request.getParameter("cPassword");
            String newName = request.getParameter("name");
            RequestDispatcher view = getServletContext().getRequestDispatcher("/session/edit.jsp");
            if (password.isEmpty()) {
                errors += "Password can't be empty. </ br>";      
            }
            if(!(newPassword.isEmpty())) {
                log(newName);
                if (!newPassword.equals(cPassword)) {
                    errors += "Passwords don't match. </ br>";
                    request.setAttribute("errors", errors);
                    view.forward(request, response);
                }
                else {
                    try {
                        if (!Password.check(password, userSessionBean.getUser().getPassword())) {
                            request.setAttribute("errors", "Password is wrong");
                            view.forward(request, response);
                        }
                        else {
                            UsersEntity e = userSessionBean.getUser();
                            String ePassword = encodePassword(newPassword);
                            e.setPassword(ePassword);
                            usersEntityFacade.edit(e);
                        }
                    } catch (Exception ex) {
                        request.setAttribute("errors", "Password can't be empty");
                        view.forward(request, response);
                    }
                }
            }
            if(!(newName.isEmpty())) {
                log("name is not empty!");
                try {
                    if (!Password.check(password, userSessionBean.getUser().getPassword())) {
                        request.setAttribute("errors", "Password is wrong");
                        view.forward(request, response);
                    }
                    else {
                        UsersEntity e = userSessionBean.getUser();
                        e.setName(newName);
                        usersEntityFacade.edit(e);
                        userSessionBean.getUser().setName(newName);
                        context = new InitialContext();
                        userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
                        userSessionBean.setUser(e);
                        HttpSession clientSession = request.getSession(true);
                        clientSession.setAttribute("uSessionBean", userSessionBean);
                        log("xxxxxxxx");
                        log("CHANGING NAME");
                        log(newName);
                        
                    }
                    } catch (Exception ex) {
                        request.setAttribute("errors", "Password can't be empty");
                        view.forward(request, response);
                    }       
            }
            log("WOW");
            response.sendRedirect("/");
//            processRequest(request, response);    
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
    
    
    private String encodePassword(String password) throws Exception {
        return Password.getSaltedHash(password);
    }

}
