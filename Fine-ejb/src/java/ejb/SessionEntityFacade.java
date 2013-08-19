/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.EJB;
import ejb.UsersEntity;

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
        UsersEntity user = usersEntityFacade.findByEmail(email);
        return (SessionEntity)em.createQuery(
            "SELECT c FROM sessionentity c WHERE c.userId = :userId")
                .setParameter("userId", user.getId())
                .getSingleResult();
    }
    
}
