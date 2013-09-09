/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.war.actions;

import im.yuri.fine.ejb.UserSessionBeanRemote;
import im.yuri.fine.ejb.entities.BlogEntryEntity;
import im.yuri.fine.ejb.entities.UsersEntity;
import im.yuri.fine.ejb.entities.VoteEntity;
import im.yuri.fine.ejb.entities.facades.BlogEntryEntityFacade;
import im.yuri.fine.ejb.entities.facades.UsersEntityFacade;
import im.yuri.fine.ejb.entities.facades.VoteEntityFacade;
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
 * @author admin
 */
@WebServlet(name = "BlogEntryActions", urlPatterns = {"/upvote", "/edit"})
public class BlogEntryActions extends HttpServlet {
    
    @EJB
    private BlogEntryEntityFacade blogEntryEntityFacade;
    
    @EJB
    private VoteEntityFacade voteEntityFacade;
    
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
        String[] splittedUrl = request.getRequestURL().toString().split("/");
        InitialContext context;
        UserSessionBeanRemote userSessionBean;
        try {
            context = new InitialContext();
            userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
        } catch (NamingException ex) {
            Logger.getLogger(CreateBlogEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        userSessionBean = (UserSessionBeanRemote)request.getSession().getAttribute("uSessionBean");
        log("WWWWOOOOWW");
        log(splittedUrl.toString());
        if(splittedUrl[splittedUrl.length-1].equals("edit")) {
            BlogEntryEntity e = blogEntryEntityFacade.find(Long.parseLong(request.getParameter("entry")));
            if (userSessionBean.getUser().getId().equals(e.getUser().getId())) {
                request.setAttribute("title", e.getTitle());
                request.setAttribute("body", e.getBody());
                request.setAttribute("id", e.getId());
            }
            else {
                response.sendRedirect("/");
            }
            RequestDispatcher view = request.getRequestDispatcher("/blogs/edit.jsp");
            view.forward(request, response);
        }
        else {

        }

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
        RequestDispatcher view;
        String[] splittedUrl = request.getRequestURL().toString().split("/");
        InitialContext context;
        UserSessionBeanRemote userSessionBean;
        try {
            context = new InitialContext();
            userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
        } catch (NamingException ex) {
            Logger.getLogger(CreateBlogEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        userSessionBean = (UserSessionBeanRemote)request.getSession().getAttribute("uSessionBean");
        if (splittedUrl[splittedUrl.length-1].equals("edit")) {
            String body = request.getParameter("body");
            String title = request.getParameter("title");
            BlogEntryEntity e = blogEntryEntityFacade.find(Long.parseLong(request.getParameter("entryId")));
            e.setBody(body);
            e.setTitle(title);
            blogEntryEntityFacade.edit(e);
            response.sendRedirect("/blog");
            
        }
        else if(splittedUrl[splittedUrl.length-1].equals("upvote")) {
            Long entryId = Long.parseLong(request.getParameter("entryId"));
            BlogEntryEntity e = blogEntryEntityFacade.find(entryId);
            e.setRating(e.getRating() + 1);
            UsersEntity user = usersEntityFacade.find(userSessionBean.getUser().getId());
            VoteEntity v = new VoteEntity();
            v.setEntry(e);
            v.setUser(user);
            voteEntityFacade.create(v);
            blogEntryEntityFacade.edit(e);
            response.setContentType("application/json");
            response.getWriter().write("{\"votes\": " + Integer.toString(e.getRating()) + "}");
        }
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
