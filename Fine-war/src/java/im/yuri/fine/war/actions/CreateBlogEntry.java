/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.war.actions;

import im.yuri.fine.ejb.UserSessionBeanRemote;
import im.yuri.fine.ejb.entities.BlogEntryEntity;
import im.yuri.fine.ejb.entities.facades.BlogEntryEntityFacade;
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

/**
 *
 * @author yuri
 */
@WebServlet(name = "CreateBlogEntry", urlPatterns = {"/new_entry"})
public class CreateBlogEntry extends HttpServlet {
    
    
    @EJB
    private BlogEntryEntityFacade blogEntryEntityFacade;

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
        RequestDispatcher view = request.getRequestDispatcher("/blogs/newEntry.jsp");
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
        String body = request.getParameter("body");
        String title = request.getParameter("title");
        InitialContext context;
        UserSessionBeanRemote userSessionBean;
        try {
            context = new InitialContext();
            userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
        } catch (NamingException ex) {
            Logger.getLogger(CreateBlogEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        userSessionBean = (UserSessionBeanRemote)request.getSession().getAttribute("uSessionBean");
        BlogEntryEntity e = new BlogEntryEntity();
        e.setBody(body);
        e.setTitle(title);
        e.setUserId(userSessionBean.getUser());
        blogEntryEntityFacade.create(e);
        response.sendRedirect("/home");
//        RequestDispatcher view = request.getRequestDispatcher("")
//        processRequest(request, response);
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
