/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.IOException;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Pekka
 */
@Controller
public class GalleryController {
    @Autowired
    PictureService pictureService;
    @Autowired
    AccountService accountService;
    @Autowired
    CommentService commentService;
    @Autowired
    Reaction_pictureRepository reaction_pictureRepository;
    
    @GetMapping("/gallery")
    public String showGallery(Model model, Principal principal){
        Account a = accountService.find(principal.getName());
        model.addAttribute("account", a);
        model.addAttribute("pictures", pictureService.findByAccount(a));
        boolean oma = true;
        model.addAttribute("oma", oma);
        
        return "gallery";
    }
    
    @GetMapping("/gallery/{id}")
    public String showGallery(@PathVariable Long id, Model model, Principal principal){
        Account a = accountService.getOne(id);
        model.addAttribute("account", a);
        model.addAttribute("pictures", pictureService.findByAccount(a));
        boolean oma =false;
        if (a == accountService.find(principal.getName())) oma=true;
        model.addAttribute("oma", oma);
        return "gallery";
    }
    
    @GetMapping("/pictures/{id}/content")
    public ResponseEntity<byte[]> getPicture(@PathVariable Long id){
        return pictureService.getPicture(id);
    }
    
    /*
    @GetMapping("/pictures/{id}/likes")
    public ResponseEntity<Long> getLikesForPicture(@PathVariable Long id){
        Picture p = pictureService.getPictureObject(id);
        Long likes = reaction_pictureRepository.countByPicture(p);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }
    */
    
    @GetMapping("/pictures/{id}/likes")
    @ResponseBody
    public Long getLikesForPicure(@PathVariable Long id){
        Picture p = pictureService.getPictureObject(id);
        return reaction_pictureRepository.countByPicture(p);
    }
    
    
    @GetMapping("/pictures/{id}/like")
    public String likePicture(@PathVariable Long id, Principal principal){
        Account a = accountService.find(principal.getName());
        Picture p = pictureService.getPictureObject(id);
        for (Reaction_picture rp : reaction_pictureRepository.findByPicture(p)){
            if (rp.getAccount()==a){
                return "redirect:/gallery/"+p.getAccount().getId();
            }
        }
        reaction_pictureRepository.save(new Reaction_picture(a,p,"1"));
        return "redirect:/gallery/"+p.getAccount().getId();
    }
    
    
    
    @GetMapping("/pictures/{id}")
    public String picturePage(@PathVariable Long id, Model model){
        Picture p = pictureService.getPictureObject(id);
        model.addAttribute("picture",p);
        model.addAttribute("comments", commentService.getCommentsForPicture(id));
        model.addAttribute("likes",reaction_pictureRepository.countByPicture(p));
        return "picture";
    }
   
    @GetMapping("/pictures/{id}/delete")
    public String deletePicture(@PathVariable Long id, Principal principal){
        Account a = accountService.find(principal.getName());
        if (pictureService.getPictureObject(id).getAccount() == a) pictureService.deletePicture(id);
        return "redirect:/gallery";
    }
    
    @PostMapping("/pictures")
    public String lisaaKuva(@RequestParam("picture") MultipartFile picture, @RequestParam String text, Principal principal) throws IOException {
        Account a = accountService.find(principal.getName());
        if (pictureService.findByAccount(a).size() < 10) pictureService.save(picture, text, a);
        
        return "redirect:/gallery";
    }
   
    @PostMapping("/pictures/{id}/comment")
    public String kommentoiKuvaa(@PathVariable Long id, String comment, Principal principal){
        commentService.commentPicture(id, comment, accountService.find(principal.getName()));
        return "redirect:/pictures/"+id;
    }

}
