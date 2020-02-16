package projekti;

import java.security.Principal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String frontPage(Principal principal) {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
            return "redirect:/feed";
        }
        else {
            return "index";
        }
    }
    /*
    @GetMapping("*")
    public String defaultPage() {
        return "redirect:/";
    }
*/
}
