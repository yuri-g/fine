/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.ejb.entities.facades;

import im.yuri.fine.ejb.entities.NewsEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class NewsEntityFacade extends AbstractFacade<NewsEntity> {
    @PersistenceContext(unitName = "Fine-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NewsEntityFacade() {
        super(NewsEntity.class);
    }
    
}
