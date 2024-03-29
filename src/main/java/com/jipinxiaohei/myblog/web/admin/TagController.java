package com.jipinxiaohei.myblog.web.admin;


import com.jipinxiaohei.myblog.po.Tag;
import com.jipinxiaohei.myblog.service.TagService;
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
public class TagController {
    @Autowired
    private TagService tagService;

    //标签

    @GetMapping("/tags")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        tagService.listTag(pageable);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags_input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",tagService.getTag(id));
        return "admin/tags_input";
    }



    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag type1 = tagService.getTagByName(tag.getName());
            if (type1!=null) {
                result.rejectValue("name","nameError","不能添加重复标签");
                return "admin/tags_input";
            }
           try {
               Tag t =  tagService.saveTag(tag);
               if(t == null){
                   attributes.addFlashAttribute("message","新增失败");
               }else {
                   attributes.addFlashAttribute("message","新增成功");
               }
               return "redirect:/admin/tags";
           }catch (Exception e){
               result.rejectValue("name","nameError","标签名称不能为空");
               return "admin/tags_input";
           }
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable long id, RedirectAttributes attributes){
        Tag type1 = tagService.getTagByName(tag.getName());
        if (type1!=null) {
            result.rejectValue("name","nameError","不能添加重复标签");
            return "admin/tags_input";
        }
        try {
            Tag t =  tagService.updateTag(id,tag);
            if(t == null){
                attributes.addFlashAttribute("message","更新失败");
            }else {
                attributes.addFlashAttribute("message","更新成功");
            }
            return "redirect:/admin/tags";
        }catch (Exception e){
            result.rejectValue("name","nameError","标签名称不能为空");
            return "admin/tags_input";
        }
    }

    @GetMapping("tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }



}
