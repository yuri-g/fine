/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.war.actions;

import im.yuri.fine.ejb.UserSessionBeanRemote;
import im.yuri.fine.ejb.entities.PersistedSessionEntity;
import im.yuri.fine.ejb.entities.SessionEntity;
import im.yuri.fine.ejb.entities.UsersEntity;
import im.yuri.fine.ejb.entities.facades.PersistedSessionEntityFacade;
import im.yuri.fine.ejb.entities.facades.SessionEntityFacade;
import im.yuri.fine.ejb.entities.facades.UsersEntityFacade;
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
import im.yuri.fine.war.util.Password;
import java.security.SecureRandom;
import java.util.Random;
import javax.naming.InitialContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author yuri
 */
@WebServlet(name = "CreateSession", urlPatterns = {"/log_in"})
public class CreateSession extends HttpServlet {
    @EJB
    private PersistedSessionEntityFacade persistedSessionEntityFacade;

    
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
        if (request.getAttribute("uSessionBean") != null) {
            response.sendRedirect("/");
        }
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
            if(user != null) {
                if (!Password.check(ePassword, user.getPassword())) {
                    RequestDispatcher view = getServletContext().getRequestDispatcher("/user/login.jsp");
                    request.setAttribute("errors", "Something went wrong!");
                    view.forward(request, response);
                }
                else {
                    SessionEntity e = sessionEntityFacade.findByUserEmail(email);
                    InitialContext context = new InitialContext();
                    UserSessionBeanRemote userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
                    Random randomGenerator = new Random();
                    int randomInt = randomGenerator.nextInt(100);
                    HttpSession clientSession = request.getSession(false);
                    clientSession.setAttribute("myStatefulBean", userSessionBean);
                    log("XXXXXXXXXX2222222222222222222222222222222222222222");
                    if(request.getParameter("remember") != null) {
                        String persistedSession = Base64.encodeBase64String(SecureRandom.getInstance("SHA1PRNG").generateSeed(128));
                        PersistedSessionEntity pSession = new PersistedSessionEntity();
                        pSession.setHash(persistedSession);
                        pSession.setUser(user);
                        persistedSession = persistedSession + "$" + user.getEmail();
                        Cookie c = new Cookie("pLogin", persistedSession);
                        response.addCookie(c);
                        persistedSessionEntityFacade.create(pSession);

                    }
                    else {
                        if (e != null) {
                            sessionEntityFacade.remove(e);    
                        }
                        e = new SessionEntity();
                        e.setUser(user);
                        e.setHash(request.getSession().getId());
                        sessionEntityFacade.create(e);
                    }

                    response.sendRedirect("/ListUsers");
                }    
           }
            else {
                log("whtya");
                response.sendRedirect("/log_in");
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
