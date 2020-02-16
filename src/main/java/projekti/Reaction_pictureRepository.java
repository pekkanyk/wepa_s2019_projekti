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
public interface Reaction_pictureRepository extends JpaRepository<Reaction_picture, Long>{
    //Reaction_picture findByAccount(Account account);
    List<Reaction_picture> findByPicture(Picture picture);
    long countByPicture(Picture picture);
    
}
