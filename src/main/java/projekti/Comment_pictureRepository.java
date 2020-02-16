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
public interface Comment_pictureRepository extends JpaRepository<Comment_picture, Long>{
    List<Comment_picture> findByPicture(Picture picture);
    List<Comment_picture> findByPictureIn(List<Picture> pictures, Pageable pageable);
    long deleteByPicture(Picture picture);
}
