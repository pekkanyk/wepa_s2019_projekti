/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Pekka
 */
public interface FollowedRepository extends JpaRepository<Followed, Long>{
    //List<Followed> findByFollowerId(Long id);
    List<Followed> findByFollowed(Account account);
    List<Followed> findByAccount(Account account);
    Followed findByAccountAndFollowed(Account account, Account followed);
}
