/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Pekka
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture extends AbstractPersistable<Long>{
 /*   
    @ManyToOne
    private Account account;
    @OneToMany (mappedBy="picture")
    private List<Comment_picture> comment_picture;
    private String text;
    private String contentType;
    private Long contentLength;
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "commission_fee_info", columnDefinition = "LONGVARBINARY")
    private byte[] picture;  
*/
    @ManyToOne
    private Account account;
    @OneToMany (mappedBy="picture")
    private List<Comment_picture> comment_picture;
    private String text;
    private String contentType;
    private Long contentLength;
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] picture;  
    
}
