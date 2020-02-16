/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Pekka
 */
@Controller
public class FeedController {
    @Autowired
    AccountService accountService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    FollowedRepository followedRepository;
    @Autowired
    Reaction_postRepository reaction_postRepository;
        
    @GetMapping("/feed")
    public String list(Model model, Principal principal) {
        Long id = accountService.find(principal.getName()).getId();
        model.addAttribute("account", accountService.getOne(id));
        model.addAttribute("posts", postService.getPostForIdWithFollowers(id));
        return "feed";
    }
    
    @GetMapping("/feed/{alias}")
    public String getFeed(@PathVariable String alias, Model model, Principal principal){
        
        if (StringUtils.isNumber(alias)){
            Long id = Long.valueOf(alias);
            return "redirect:/feed/"+accountService.getOne(id).getAlias();
        }
        
        Account a = accountService.fingByAlias(alias);
        if (a!=null) {
        //Account a = accountService.fingByAlias(alias);
        model.addAttribute("account", a);
        Account oma = accountService.find(principal.getName());
        
        boolean seurattu = followedRepository.findByAccountAndFollowed(oma, a) == null;
        model.addAttribute("seurattu", seurattu);
        model.addAttribute("posts", postService.getPostsFor(a.getId()));
        return "feed";
        }
        
        return "redirect:/feed";
    }
    
    @GetMapping("/post/{id}")
    public String postPage(@PathVariable Long id, Model model){
        Post p = postService.getPostWithId(id);
        model.addAttribute("post", p);
        model.addAttribute("comments", commentService.getCommentsForPost(id));
        model.addAttribute("likes", reaction_postRepository.countByPost(p));
        return "post";
    }
    
    @GetMapping("/post/{id}/like")
    public String likePost(@PathVariable Long id, Principal principal){
        Account a = accountService.find(principal.getName());
        Post p = postService.getPostWithId(id);
        for (Reaction_post rp : reaction_postRepository.findByPost(p)){
            if (rp.getAccount()==a){
                return "redirect:/feed/"+p.getAccount().getId();
            }
        }
        reaction_postRepository.save(new Reaction_post(a,p,"1"));
        return "redirect:/feed/"+p.getAccount().getId();
    }
    /*
    @GetMapping(path="/resources/post/{id}", produces="application/json")
    @ResponseBody
    public List<Comment_post> postJson(@PathVariable Long id){
        return commentService.getCommentsForPost(id);
    }
    */
    
    @PostMapping("/feed/post")
    public String post(Principal principal, @RequestParam String post){
        Long id = accountService.find(principal.getName()).getId();
        postService.putPost(id, post);
        return "redirect:/feed";
    }
    
    @PostMapping("/post/{id}/comment")
    public String kommentoiPostausta(@PathVariable Long id, String comment, Principal principal){
        commentService.commentPost(id, comment, accountService.find(principal.getName()));
        return "redirect:/post/"+id;
    }
    
    @PostMapping("/search")
    public String search(@RequestParam String haku, Model model){
        model.addAttribute("users", accountService.findByFullNameUsernameAlias(haku));
        return "search";
    }
    
    @PostMapping("/feed/follow")
    public String seuraa(@RequestParam Long id, Principal principal){
        Followed f = new Followed();
        f.setFollowed(accountService.getOne(id));
        f.setAccount(accountService.find(principal.getName()));
        f.setTimestamp(LocalDateTime.now());
        followedRepository.save(f);
        return "redirect:/feed/"+id;
    }
    
    @PostMapping("/feed/unfollow")
    public String unfollow(@RequestParam Long id, Principal principal){
        Account a = accountService.find(principal.getName());
        if (followedRepository.getOne(id).getAccount() == a || followedRepository.getOne(id).getFollowed()==a){
            followedRepository.deleteById(id);
        }
        return "redirect:/tiedot";
    }
    
}
