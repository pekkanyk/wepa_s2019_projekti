/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Pekka
 */
public interface Reaction_postRepository extends JpaRepository<Reaction_post, Long>{
    //Reaction_post findByAccount(Account account);
    List<Reaction_post> findByPost(Post post);
    long countByPost(Post post);
    
}
