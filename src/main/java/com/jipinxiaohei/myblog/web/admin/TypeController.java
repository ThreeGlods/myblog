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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    //分类

    @GetMapping("/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        typeService.listType(pageable);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types_input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types_input";
    }



    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypebyName(type.getName());
            if (type1!=null) {
                result.rejectValue("name","nameError","不能添加重复分类");
                return "admin/types_input";
            }
           try {
               Type t =  typeService.saveType(type);
               if(t == null){
                   attributes.addFlashAttribute("message","新增失败");
               }else {
                   attributes.addFlashAttribute("message","新增成功");
               }
               return "redirect:/admin/types";
           }catch (Exception e){
               result.rejectValue("name","nameError","分类名称不能为空");
               return "admin/types_input";
           }
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypebyName(type.getName());
        if (type1!=null) {
            result.rejectValue("name","nameError","不能添加重复分类");
            return "admin/types_input";
        }
        try {
            Type t =  typeService.updateType(id,type);
            if(t == null){
                attributes.addFlashAttribute("message","更新失败");
            }else {
                attributes.addFlashAttribute("message","更新成功");
            }
            return "redirect:/admin/types";
        }catch (Exception e){
            result.rejectValue("name","nameError","分类名称不能为空");
            return "admin/types_input";
        }
    }

    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }



}
