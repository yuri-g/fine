/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.war.actions;

//import im.yuri.fine.ejb.UserSessionBean;
import im.yuri.fine.ejb.UserSessionBeanRemote;
import im.yuri.fine.ejb.entities.PersistedSessionEntity;
import im.yuri.fine.ejb.entities.SessionEntity;
import im.yuri.fine.ejb.entities.facades.PersistedSessionEntityFacade;
import im.yuri.fine.ejb.entities.facades.SessionEntityFacade;
import im.yuri.fine.ejb.entities.facades.UsersEntityFacade;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author yuri
 */
@WebFilter(filterName = "NewFilter", urlPatterns = {"/*"})
public class NewFilter implements Filter {
    @EJB
    PersistedSessionEntityFacade persistedSessionEntityFacade;
//    UserSessionBean userSessionBean = lookupUserSessionBeanBean();
     
    private static final boolean debug = true;
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    
    @EJB
    private SessionEntityFacade sessionEntityFacade;
    
    @EJB
    private UsersEntityFacade usersEntityFacade;
    
    
    
    
    
   
    
    public NewFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("NewFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.

        // For example, a logging filter might log items on the request object,
        // such as the parameters.
//	
//         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
//         String name = (String)en.nextElement();
//         String values[] = request.getParameterValues(name);
//         int n = values.length;
//         StringBuffer buf = new StringBuffer();
//         buf.append(name);
//         buf.append("=");
//         for(int i=0; i < n; i++) {
//         buf.append(values[i]);
//         if (i < n-1)
//         buf.append(",");
//         }
//         log(buf.toString());
//         }
        
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("NewFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.

        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
	/*
         for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         Object value = request.getAttribute(name);
         log("attribute: " + name + "=" + value.toString());

         }
         */

        // For example, a filter might append something to the response.
	
//         PrintWriter respOut = new PrintWriter(response.getWriter());
//         respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        
        InitialContext context;
        UserSessionBeanRemote userSessionBean;
        try {
            context = new InitialContext();
            userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
        } catch (NamingException ex) {
            
        }
        
        
        if (debug) {
            log("NewFilter:doFilter()");
        }
        
        HttpServletRequest req = (HttpServletRequest) request;
        userSessionBean = (UserSessionBeanRemote)req.getSession().getAttribute("uSessionBean");
        if (userSessionBean == null) {
           try {
                 
               userSessionBean = checkLogin(req,  userSessionBean);
           }
           catch (NamingException ex) {
               Logger.getLogger(ListUsers.class.getName()).log(Level.SEVERE, null, ex);
           }
            
            if (userSessionBean != null) {
                req.setAttribute("uSessionBean", userSessionBean);
            }
           
        }
        

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }
        
        doAfterProcessing(req, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("NewFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("NewFilter()");
        }
        StringBuffer sb = new StringBuffer("NewFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
//
//    private UserSessionBean lookupUserSessionBeanBean() {
//        try {
//            Context c = new InitialContext();
//            return (UserSessionBean) c.lookup("java:global/Fine/Fine-ejb/UserSessionBean!im.yuri.fine.ejb.UserSessionBean");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }

    private PersistedSessionEntityFacade lookupPersistedSessionEntityFacadeBean() {
        try {
            Context c = new InitialContext();
            return (PersistedSessionEntityFacade) c.lookup("java:global/Fine/Fine-ejb/PersistedSessionEntityFacade!im.yuri.fine.ejb.entities.facades.PersistedSessionEntityFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private UserSessionBeanRemote checkLogin(HttpServletRequest req, UserSessionBeanRemote userSessionBean) throws NamingException {
            SessionEntity e = sessionEntityFacade.findByHash(req.getSession().getId());
            InitialContext context = new InitialContext();
            
            userSessionBean = (UserSessionBeanRemote) context.lookup("ejb/userSessionBean");
            if (e == null) {
                Cookie[] cookies = req.getCookies();
                Cookie persistedCookie = null;
                if(cookies != null) {
                     for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("pLogin")) {
                        persistedCookie = cookies[i];
                    }
                }
                if (persistedCookie != null) {
                   String[] splittedCookie = persistedCookie.getValue().split("\\$");
                   log(persistedCookie.getValue().split("$")[0]);
                   String userEmail = splittedCookie[1];
                   String hash = splittedCookie[0];
                   PersistedSessionEntity persistedSession = persistedSessionEntityFacade.findByUserAndHash(userEmail, hash);
                   if(persistedSession != null) {
                       
                       userSessionBean.setUser(persistedSession.getUser());
                       return userSessionBean;
                   }
                }
                else {
                    return null;
                }
                }
               
            }
            else {
                userSessionBean.setUser(e.getUser());
                return userSessionBean;
            }
            return null;
    }
}
