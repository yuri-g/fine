/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.war.actions;

import im.yuri.fine.ejb.UserSessionBeanRemote;
import im.yuri.fine.ejb.entities.PersistedSessionEntity;
import im.yuri.fine.ejb.entities.SessionEntity;
import im.yuri.fine.ejb.entities.facades.PersistedSessionEntityFacade;
import im.yuri.fine.ejb.entities.facades.SessionEntityFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yuri
 */
@WebServlet(name = "DestroySession", urlPatterns = {"/logout"})
public class DestroySession extends HttpServlet {

    @EJB
    private SessionEntityFacade sessionEntityFacade;
    
    @EJB
    private PersistedSessionEntityFacade persistedSessionEntityFacade;
    
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
        UserSessionBeanRemote userSessionBean;
        
        userSessionBean = (UserSessionBeanRemote) request.getSession(false).getAttribute("uSessionBean");
        if (userSessionBean != null) {
           
            SessionEntity e = sessionEntityFacade.findByHash(request.getSession().getId());
            if (e != null) {
                sessionEntityFacade.remove(e);
            }
            Cookie[] cookies = request.getCookies();
            Cookie persistedCookie = null;
            if(cookies != null) {
                 for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("pLogin")) {
                        persistedCookie = cookies[i];
                    }
                }
                if(persistedCookie != null) {
                    String[] splittedCookie = persistedCookie.getValue().split("\\$");
                    String userEmail = splittedCookie[1];
                    String hash = splittedCookie[0];
                    PersistedSessionEntity persistedSession = persistedSessionEntityFacade.findByUserAndHash(userEmail, hash);
                    if (persistedSession != null) {
                        persistedSessionEntityFacade.remove(persistedSession);
                    }
                    persistedCookie.setMaxAge(0);
                    response.addCookie(persistedCookie);

                }
               
            }
        InitialContext context;
        
        try {
            context = new InitialContext();
            userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
        } catch (NamingException ex) {
            
        }
        userSessionBean = (UserSessionBeanRemote)request.getSession(false).getAttribute("uSessionBean");
        log(userSessionBean.getClass().toString());
        request.getSession(false).setAttribute("uSessionBean", null);
        userSessionBean.remove();
//        log(userSessionBean.getUser().getName().toString());
        response.sendRedirect("/");
            
        }
        else {
            response.sendRedirect("/log_in");
        }
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
    }
}
