/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Pekka
 */
@Controller
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    FollowedRepository followedRepository;

    @GetMapping("/accounts")
    public String list(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "accounts";
    }
    
    @PreAuthorize("hasrole(('ADMIN')")
    @GetMapping("/manageAccounts")
    public String manage(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "accounts_management";
    }

    @PostMapping("/accounts")
    public String add(@RequestParam String username, @RequestParam String password, @RequestParam String fullName, @RequestParam String alias) {
        accountService.create(username, password, fullName, alias);
        return "redirect:/";
    }
    
    @GetMapping("/tiedot")
    public String tiedot(Model model, Principal principal){
        Account oma = accountService.find(principal.getName());
        model.addAttribute("account", oma);
        model.addAttribute("seuratut", followedRepository.findByAccount(oma));
        model.addAttribute("seuraajat", followedRepository.findByFollowed(oma));
               
        return "tiedot";
    }
    
    @PostMapping("/tiedot")
    public String editInfo(@RequestParam String fullName, @RequestParam String password, @RequestParam String alias, Principal principal) {
        Long id = accountService.find(principal.getName()).getId();
        accountService.updateInfo(id, password, fullName, alias);
        return "redirect:/feed";
    }
    @PostMapping("/tiedot/setprofilepicture")
    public String setProfilePicture(@RequestParam Long picId, Principal principal){
        Account a = accountService.find(principal.getName());
        accountService.setProfilePicture(a.getId(), picId);
        return "redirect:/gallery";
    }
    
    @PreAuthorize("hasrole(('ADMIN')")
    @PostMapping("/manageAccounts/{id}/addRole")
    public String addRole(@PathVariable Long id, @RequestParam String role) {
        accountService.addRole(id, role);
        return "redirect:/manageAccounts";
    }
    
    @PreAuthorize("hasrole(('ADMIN')")
    @PostMapping("/manageAccounts/{id}/remRole")
    public String remRole(@PathVariable Long id, @RequestParam String role) {
        accountService.remRole(id, role);
        return "redirect:/manageAccounts";
    }

}
