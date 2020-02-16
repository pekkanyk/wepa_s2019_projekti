/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Pekka
 */
@Service
public class PictureService {
    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    Comment_pictureRepository comment_pictureRepository;
    
    
    public ResponseEntity<byte[]> getPicture(Long id){
        Picture p = pictureRepository.getOne(id);
        
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(p.getContentType()));
        headers.setContentLength(p.getContentLength());
        
        return new ResponseEntity<>(p.getPicture(),headers,HttpStatus.CREATED);
    }
    
    
    public Picture getPictureObject(Long id){
        return pictureRepository.getOne(id);
    }
    
    
    public String save(MultipartFile picture, String text, Account account) throws IOException{
        Picture p = new Picture();
        p.setContentType(picture.getContentType());
        p.setText(text);
        p.setAccount(account);
        byte[] bytes = IOUtils.toByteArray(picture.getInputStream());
        p.setPicture(bytes);
        p.setContentLength(picture.getSize());
        pictureRepository.save(p);
        return "redirect:/gallery";
    }
    
    
    public List<Picture> findByAccount(Account account){
        return pictureRepository.findByAccount(account);
    }
    
    @Transactional
    public void deletePicture(Long id){
        Picture p = pictureRepository.getOne(id);
        comment_pictureRepository.deleteByPicture(p);
        pictureRepository.deleteById(id);
    }
}
