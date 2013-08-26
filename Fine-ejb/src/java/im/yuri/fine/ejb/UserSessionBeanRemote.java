/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.ejb;

import im.yuri.fine.ejb.entities.UsersEntity;
import javax.ejb.Remote;

/**
 *
 * @author yuri
 */

@Remote
public interface UserSessionBeanRemote {
    public void initialize(UsersEntity e);
    public UsersEntity getUser();
    public void remove();
    public void setUser(UsersEntity user);
    public void setSessionHash(String hash);
    public String getSessionHash();

}
