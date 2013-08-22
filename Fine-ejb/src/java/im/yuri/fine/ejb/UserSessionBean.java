/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.ejb;

import im.yuri.fine.ejb.entities.UsersEntity;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 *
 * @author yuri
 */
@Stateful(mappedName = "ejb/userSessionBean")
public class UserSessionBean implements UserSessionBeanRemote {
    
    private UsersEntity user;
    private String sessionHash;
    private int test;

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    
    public void initialize(UsersEntity e) {
        
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public String getSessionHash() {
        return sessionHash;
    }

    public void setSessionHash(String sessionHash) {
        this.sessionHash = sessionHash;
    }
    
    @Remove
    public void remove() {
        
    }

}
