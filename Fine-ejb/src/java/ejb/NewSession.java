/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author yuri
 */
@JMSDestinationDefinition(name = "jms/NewSession", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "NewSession")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NewSession")
})


public class NewSession implements MessageListener {
    
    @PersistenceContext(unitName = "Fine-ejbPU")
    private EntityManager em;
    @Resource
    private MessageDrivenContext mdc;
    
    public NewSession() {
    }
    
    @Override
    public void onMessage(Message message) {
         ObjectMessage msg = null;
        try {
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
                SessionEntity e = (SessionEntity) msg.getObject();
                if ("create".equals(msg.getJMSType())) {
                    save(e);
                }
                else if("remove".equals(msg.getJMSType())) {
                    SessionEntity toBeRemoved = em.merge(e);
                    remove(toBeRemoved);
                }
                
                
            }
        }
        catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        }
        catch (Throwable te) {
            te.printStackTrace();
        }
        

    }
    
    public void save(Object object) {
        em.persist(object);
    }
    
    public void remove(Object object) {
        em.remove(object);
    }
}
