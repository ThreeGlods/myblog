package com.jipinxiaohei.myblog.web.admin;

import com.jipinxiaohei.myblog.po.Blog;
import com.jipinxiaohei.myblog.po.User;
import com.jipinxiaohei.myblog.service.BlogService;
import com.jipinxiaohei.myblog.service.TagService;
import com.jipinxiaohei.myblog.service.TypeService;
import com.jipinxiaohei.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.jar.Attributes;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT="admin/blogs_input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3, sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        //初始化
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        System.out.println("111---111");
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        System.out.println("信息："+b.getTitle()+b.getUpdateTime());
        if (b==null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }

        return REDIRECT_LIST;
    }
}
