/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pekka
 */
@Service
public class CommentService {
    @Autowired 
    Comment_pictureRepository comment_pictureRepository;
    @Autowired
    Comment_postRepository comment_postRepository;
    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    PostRepository postRepository;
    
    public void commentPicture(Long id, String comment, Account account){
        Comment_picture c = new Comment_picture();
        c.setPicture(pictureRepository.getOne(id));
        c.setComment(comment);
        c.setAikaleima(LocalDateTime.now());
        c.setAccount(account);
        comment_pictureRepository.save(c);
    }
   /* 
    public List<Comment_picture> getCommentsForPicture(Long id){
        return comment_pictureRepository.findByPicture(pictureRepository.getOne(id));
    }
    */
    public List<Comment_picture> getCommentsForPicture(Long id){
        Picture p = pictureRepository.getOne(id);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("aikaleima").descending());
        List <Picture> pictures = new ArrayList<>();
        pictures.add(p);
        return comment_pictureRepository.findByPictureIn(pictures,pageable);
    }
    /*
    public List<Comment_post> getCommentsForPost(Long id){
        return comment_postRepository.findByPost(postRepository.getOne(id));
    }
    */
    public List<Comment_post> getCommentsForPost(Long id){
        Post p = postRepository.getOne(id);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("aikaleima").descending());
        List <Post> posts = new ArrayList<>();
        posts.add(p);
        return comment_postRepository.findByPostIn(posts,pageable);
    }
    
    public void commentPost(Long id, String Comment, Account account){
        Comment_post c = new Comment_post();
        c.setPost(postRepository.getOne(id));
        c.setAccount(account);
        c.setAikaleima(LocalDateTime.now());
        c.setComment(Comment);
        comment_postRepository.save(c);
    }
}
