package com.jipinxiaohei.myblog.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class Conntroller {
    @GetMapping("/blogs")
    public String blogs(){
        return "admin/blog_admin";
    }
}