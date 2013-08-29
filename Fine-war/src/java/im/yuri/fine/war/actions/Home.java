/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.war.actions;

import im.yuri.fine.ejb.UserSessionBeanRemote;
import im.yuri.fine.ejb.entities.BlogEntryEntity;
import im.yuri.fine.ejb.entities.UsersEntity;
import im.yuri.fine.ejb.entities.facades.BlogEntryEntityFacade;
import im.yuri.fine.ejb.entities.facades.UsersEntityFacade;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "Home", urlPatterns = {"/home", "/users"})
public class Home extends HttpServlet {

    @EJB
    private BlogEntryEntityFacade blogEntryEntityFacade;
    
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
        log("00000000000000000000000000");
        String[] splittedUrl = request.getRequestURL().toString().split("/");
        
        log(Integer.toString(splittedUrl.length));
        List<BlogEntryEntity> entries;
        RequestDispatcher view;
        for(int i = 0; i < splittedUrl.length; i++) {
            log(splittedUrl[i]);
        }
        if (splittedUrl[splittedUrl.length-1].equals("home")) {
//            log("home");
            InitialContext context;
            UserSessionBeanRemote userSessionBean;
            try {
                context = new InitialContext();
                userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
            }
            catch(NamingException e) {
            
            }
            userSessionBean = (UserSessionBeanRemote)request.getSession(false).getAttribute("uSessionBean");
            if (userSessionBean != null) {
                entries = blogEntryEntityFacade.findAllByEmail(userSessionBean.getUser().getEmail());
                request.setAttribute("entries", entries);
                view = request.getRequestDispatcher("/blogs/list.jsp");
                view.forward(request, response);
            }
            else {
                response.sendRedirect("/log_in");
            }
            
        }
        else if(splittedUrl[splittedUrl.length-1].equals("users")) {
            Integer userId = null;
            log("PARAM:");
            log(request.getParameter("id"));
            userId = Integer.parseInt(request.getParameter("id"));
            UsersEntity u;
            u = usersEntityFacade.findById(userId);
            if(u != null) {
                entries = blogEntryEntityFacade.findAllByEmail(u.getEmail());
                request.setAttribute("entries", entries);
                view = request.getRequestDispatcher("/blogs/list.jsp");
                view.forward(request, response);
            }
            else {
                response.sendError(404);
            }
            
            
        }
        
        

        
    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
