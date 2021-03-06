/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package im.yuri.fine.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yuri
 */
@Entity
@XmlRootElement
public class BlogEntryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    /*
    @JoinColumn(name = "userId")
    @OneToOne
    private UsersEntity userId;
    */
    
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private UsersEntity user;
    
    @OneToMany(mappedBy="entry")
    private List<VoteEntity> votes;

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
    
    @Column(columnDefinition="LONG VARCHAR")
    private String body;
    
    private String title;
    
    private int rating; 
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

//
//    public UsersEntity getUserId() {
//        return userId;
//    }
//
//    public void setUserId(UsersEntity userId) {
//        this.userId = userId;
//    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlogEntryEntity)) {
            return false;
        }
        BlogEntryEntity other = (BlogEntryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "im.yuri.fine.ejb.entities.BlogEntryEntity[ id=" + id + " ]";
    }
    
}
