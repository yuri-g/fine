/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.war.actions;

import im.yuri.fine.ejb.UserSessionBeanRemote;
import im.yuri.fine.ejb.entities.BlogEntryEntity;
import im.yuri.fine.ejb.entities.facades.BlogEntryEntityFacade;
import im.yuri.fine.ejb.entities.facades.UsersEntityFacade;
import im.yuri.fine.ejb.entities.facades.VoteEntityFacade;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "Home", urlPatterns = {"/home", ""})
public class Home extends HttpServlet {

    @EJB
    private BlogEntryEntityFacade blogEntryEntityFacade;
    
    @EJB
    private UsersEntityFacade usersEntityFacade;
    
    @EJB
    private VoteEntityFacade voteEntityFacade;
    
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
        List<BlogEntryEntity> entries;
        RequestDispatcher view;
        entries = blogEntryEntityFacade.findPopular();
        InitialContext context;
        UserSessionBeanRemote userSessionBean;
        try {
            context = new InitialContext();
            userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
        } catch (NamingException ex) {
            Logger.getLogger(CreateBlogEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        userSessionBean = (UserSessionBeanRemote)request.getSession().getAttribute("uSessionBean");
        Boolean[] voted = new Boolean[entries.size()];
        int i = 0;
        if(userSessionBean != null) {
                    for(BlogEntryEntity e: entries) {
            if (voteEntityFacade.getByEntryAndUser(e.getId(), userSessionBean.getUser().getId()) == null) {
                voted[i] = false;
            }
            else {
                voted[i] = true;
            }
            i++;
        }
        }

        request.setAttribute("popular", entries);
        request.setAttribute("voted", voted);
        view = request.getRequestDispatcher("/blogs/home.jsp");
        view.forward(request, response);
        /*
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
                entries = blogEntryEntityFacade.findAllByEmailDateDesc(userSessionBean.getUser().getEmail());
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
                int currentPage;
                String nextPageUrl ="";
                if(request.getParameter("p") == null) {
                    currentPage = 1;
                }
                else {
                    currentPage = Integer.parseInt(request.getParameter("p"));
                }
                nextPageUrl = request.getRequestURL().toString() + "?id=" + u.getId() +"&p=";
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("userId", userId);
                entries = blogEntryEntityFacade.findAllByEmailDateDesc(u.getEmail());
                int pages = (int)Math.ceil(entries.size()/5.0);
                int lastIndex;
                if(entries.size() < (((currentPage-1)*5)+5)) {
                    lastIndex = entries.size();
                }
                else {
                    lastIndex = (((currentPage-1)*5)+5);
                }
                log(Integer.toString(entries.size()));
                log(Integer.toString(lastIndex));
                List<BlogEntryEntity> perPage = entries.subList((currentPage-1)*5, lastIndex);
                request.setAttribute("pages", pages);
                request.setAttribute("entries", perPage);
                request.setAttribute("nextPageUrl", nextPageUrl);
                view = request.getRequestDispatcher("/blogs/list.jsp");
                view.forward(request, response);
            }
            else {
                response.sendError(404);
            }
            
            
            
        }*/
        
        

        
    
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
