package cn.java.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    private HttpSession session;
    @PostMapping("/index")
    public String index(String username){
        System.out.println("==============" + username);
        session.setAttribute("username",username);
        return "/index";
    }
}
