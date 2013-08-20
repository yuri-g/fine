/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.EJB;
import javax.persistence.NoResultException;

/**
 *
 * @author yuri
 */
@Stateless
public class SessionEntityFacade extends AbstractFacade<SessionEntity> {
    @PersistenceContext(unitName = "Fine-ejbPU")
    private EntityManager em;

    @EJB
    private UsersEntityFacade usersEntityFacade;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SessionEntityFacade() {
        super(SessionEntity.class);
    }
    
    
    public SessionEntity findByUserEmail(String email) {
        try {
            UsersEntity user = usersEntityFacade.findByEmail(email);
            if (user != null) {
                return (SessionEntity)em.createQuery(
                "SELECT c FROM SessionEntity c WHERE c.userId = :userId")
                .setParameter("userId", user)
                .getSingleResult();
            }
            else {
                return null;
            }
             
        }
        catch (NoResultException e) {
            return null;
        }
        

    }
    
    public SessionEntity findByHash(String hash) {
        try {
            return (SessionEntity)em.createQuery(
                "SELECT c FROM SessionEntity c WHERE c.sessionHash = :sessionHash")
                .setParameter("sessionHash", hash)
                .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }
    
}
