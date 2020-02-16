/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Pekka
 */
public interface Comment_postRepository extends JpaRepository<Comment_post, Long> {
    List<Comment_post> findByPost(Post post);
    List<Comment_post> findByPostIn(List<Post> posts, Pageable pageable);

    
}
