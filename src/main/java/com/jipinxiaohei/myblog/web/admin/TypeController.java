package com.jipinxiaohei.myblog.web.admin;


import com.jipinxiaohei.myblog.po.Type;
import com.jipinxiaohei.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        typeService.listType(pageable);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        System.out.println("22222");
        model.addAttribute("type",new Type());
        return "admin/types_input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type,BindingResult result){
            if(result.hasErrors()==false){
                System.out.println("11111"+result.getFieldError());
                return "admin/types_input";
            }
        /*Type t =  typeService.saveType(type);
        if(t == null){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }*/
        return "redirect:/admin/types";

    }
}