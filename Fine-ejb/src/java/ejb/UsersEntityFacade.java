/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author yuri
 */
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
    
}