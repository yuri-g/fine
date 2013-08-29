/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.ejb.entities.facades;

import im.yuri.fine.ejb.entities.UsersEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


@Stateless
public class UsersEntityFacade extends AbstractFacade<UsersEntity> {
    @PersistenceContext(unitName = "Fine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    


    public UsersEntityFacade() {
        super(UsersEntity.class);
    }

    
    public UsersEntity findByEmail(String email) {
        try {
          return (UsersEntity)em.createQuery(
            "SELECT c FROM UsersEntity c WHERE c.email = :userEmail")
                .setParameter("userEmail", email)
                .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
       
    }
    
    public UsersEntity findById(int id) {
        try {
            return (UsersEntity)em.createQuery(
              "SELECT c FROM UsersEntity c WHERE c.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        }
        catch(NoResultException e) {
            return null;
        }
    }
    
    
}
