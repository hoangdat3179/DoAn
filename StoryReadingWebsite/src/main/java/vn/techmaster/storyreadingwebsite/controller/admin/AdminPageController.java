package vn.techmaster.storyreadingwebsite.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminPageController {


    @GetMapping("/admin")
    public String AdminPage(){
        return "fragmentsAdmin";
    }

    @GetMapping("/admin/login")
    public String viewAdminLoginPage() {
        return "admin_login";
    }

}
