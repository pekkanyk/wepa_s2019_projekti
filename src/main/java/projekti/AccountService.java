/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pekka
 */
@Service
public class AccountService {
    
    @Autowired 
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired 
    PictureRepository pictureRepository;
    
    public Account find(String username){
        return accountRepository.findByUsername(username);
    }
    
    public Account fingByAlias(String alias){
        return accountRepository.findByAlias(alias);
    }
    
    public List<Account> findByFullNameUsernameAlias(String search){
        List<Account> tilit = accountRepository.findByFullNameIgnoreCaseContains(search);
        accountRepository.findByAliasIgnoreCaseContains(search).stream().filter((tili) -> (!tilit.contains(tili))).forEachOrdered((tili) -> {
            tilit.add(tili);
        });
        return tilit;
    }
    
    public List<Account> findAll(){
        return accountRepository.findAll();
    }
    
    public Account getOne(Long id){
        return accountRepository.getOne(id);
    }
    
    public void create(String username, String password, String fullName, String alias){
        if (this.accountRepository.findByUsername(username)==null && this.accountRepository.findByAlias(alias)==null){
            
            List<String> authorities = new ArrayList<>();
            authorities.add("USER");
            if (accountRepository.findAll().isEmpty()){
                authorities.add("ADMIN");
            }
            //Account a = new Account(username, passwordEncoder.encode(password), authorities);
            Account a = new Account();
            a.setUsername(username);
            a.setPassword(passwordEncoder.encode(password));
            a.setAlias(alias);
            a.setFullName(fullName);
            a.setAuthorities(authorities);
            accountRepository.save(a);
        }
        else {
            //jo käyttäjä on jo olemassa, tehdään jotain (tai ei mitään).
        }
    }
    
    public void addRole(Long id, String role){
        Account a = accountRepository.getOne(id);
        if (!a.getAuthorities().contains(role)){
            a.getAuthorities().add(role);
        }
        accountRepository.save(a);
    }
    
    public void remRole(Long id, String role){
        Account a = accountRepository.getOne(id);
        if (a.getAuthorities().contains(role)){
            a.getAuthorities().remove(role);
        }
        accountRepository.save(a);
    }
    
    public void updateInfo(Long id, String password, String fullName, String alias){
        Account a = accountRepository.getOne(id);
        if (!alias.isEmpty()) a.setAlias(alias);
        if (!fullName.isEmpty()) a.setFullName(fullName);
        if (!password.isEmpty()) a.setPassword(passwordEncoder.encode(password));
        accountRepository.save(a);
    }
    
    public void setProfilePicture(Long id, Long picId){
        Account a = accountRepository.getOne(id);
        Picture p = pictureRepository.getOne(picId);
        if (p.getAccount()==a){
            a.setProfilePicture(picId);
            accountRepository.save(a);
        }
        
    }
}
