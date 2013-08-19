/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package war;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import ejb.UsersEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Password;

/**
 *
 * @author yuri
 */
@WebServlet(name = "CreateUser", urlPatterns = {"/sign_in"})
public class CreateUser extends HttpServlet {

    @Resource(mappedName="jms/NewMessageFactory")
    private  ConnectionFactory connectionFactory;

    @Resource(mappedName="jms/NewUser")
    private  Queue queue;

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
        RequestDispatcher view = getServletContext().getRequestDispatcher("/user/signin.jsp");
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
        String errors = validate(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            RequestDispatcher view = getServletContext().getRequestDispatcher("/user/signin.jsp");
            view.forward(request, response);
        }
        else {
             try {
                Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer messageProducer = session.createProducer(queue);
                ObjectMessage message = session.createObjectMessage();
                
                UsersEntity e = new UsersEntity();
                e.setName(request.getParameter("username"));
                e.setEmail(request.getParameter("email"));
                String ePassword = encodePassword(request.getParameter("password"));
                e.setPassword(ePassword);
                message.setObject(e);                
                messageProducer.send(message);
                messageProducer.close();
                connection.close();
                response.sendRedirect("ListUsers");
                return;

            }      
            catch (JMSException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                Logger.getLogger(CreateUser.class.getName()).log(Level.SEVERE, null, ex);
            }
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



    private String validate(HttpServletRequest request) {
        String errors = "";
        String password = request.getParameter("password");
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        
        matcher = pattern.matcher(request.getParameter("email"));
        if (!matcher.matches()) {
            errors += "Email format is wrong. </ br>";    
        }
        if (password.isEmpty()) {
            errors += "Password can't be empty. </ br>";      
        }
        if (!password.equals(request.getParameter("passwordC"))) {
            errors += "Passwords don't match. </ br>";
        }
        return errors;

    }

    private String encodePassword(String password) throws Exception {
        return Password.getSaltedHash(password);
    }



}
