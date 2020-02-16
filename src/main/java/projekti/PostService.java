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
public class PostService {
    
    @Autowired
    PostRepository postRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FollowedRepository followedRepository;
    
    
    public List<Post> getPostsFor(Long id){
        Account a = accountRepository.getOne(id);
        Pageable pageable = PageRequest.of(0, 25, Sort.by("aikaleima").descending());
        List <Account> accounts = new ArrayList<>();
        accounts.add(a);
        return postRepository.findByAccountIn(accounts,pageable);
    }
    
    public void putPost(Long id, String post){
        Post p = new Post();
        p.setAccount(accountRepository.getOne(id));
        p.setPost(post);
        p.setAikaleima(LocalDateTime.now());
        postRepository.save(p);
    }
    
    public Post getPostWithId(Long id){
        return postRepository.getOne(id);
    }
    
    public List<Post> getPostForIdWithFollowers(Long id){
        Account a = accountRepository.getOne(id);
        Pageable pageable = PageRequest.of(0, 25, Sort.by("aikaleima").descending());
        List <Account> accounts = new ArrayList<>();
        accounts.add(a);
        followedRepository.findByAccount(a).forEach((followed) -> {
            accounts.add(followed.getFollowed());
        });
        return postRepository.findByAccountIn(accounts,pageable);
    }
}
