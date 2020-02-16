/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Pekka
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    private String username;
    private String password;
    private String fullName;
    private String alias;
    private Long profilePicture;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;
    @OneToMany (mappedBy="account")
    private List<Picture> pictures;
    @OneToMany (mappedBy="account")
    private List<Comment_picture> comments;
    @OneToMany (mappedBy="account")
    private List<Reaction_picture> reactions;
    @OneToMany (mappedBy="account")
    private List<Followed> account;
    
    @OneToMany (mappedBy="followed")
    private List<Followed> followed;
    @OneToMany (mappedBy="account")
    private List<Post> posts;

}
