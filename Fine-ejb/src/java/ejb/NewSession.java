/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
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
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NewMessage")
})
public class NewSession implements MessageListener {
    
    @PersistenceContext(unitName = "Fine-ejbPU")
    private EntityManager em;
    @Resource
    private MessageDrivenContext mdc;
    
    public NewSession() {
    }
    
  public void onMessage(Message message) {
         ObjectMessage msg = null;
        try {
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
                SessionEntity e = (SessionEntity) msg.getObject();
                save(e);
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
}
