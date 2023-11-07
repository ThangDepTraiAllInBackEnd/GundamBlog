package com.blog.controllers;

import com.blog.common.Constant;
import com.blog.common.Message;
import com.blog.common.RoleCommon;
import com.blog.dto.BlogDTO;
import com.blog.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blog")
public class BlogController {
  BlogDTO prevBlogDTO = null;
  BlogDTO prev2BlogDTO = null;
  int numOfAccess = 0;
  @Autowired
  BlogService blogService;

  @GetMapping("/")
  private String showAllBlog(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
    Page<BlogDTO> blogDTOPage = blogService.pagingAllBlog(pageNo, Constant.PAGE_SIZE, blogService.findAllBlog(RoleCommon.UN_KNOW));
    model.addAttribute("totalPage", blogDTOPage.getTotalPages());
    model.addAttribute("currentPage", pageNo);
    model.addAttribute("blogDTOList", blogDTOPage);
    return "pages/user/blogs";
  }

  @GetMapping("/search")
  private String searchBlog(Model model, @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
    if (keyword != null && keyword != "") {
      Page<BlogDTO> blogDTOPage = blogService.findBlogByTitle(keyword, pageNo, Constant.SEARCH_PAGE_SIZE, RoleCommon.UN_KNOW);
      model.addAttribute("totalPage", blogDTOPage.getTotalPages());
      model.addAttribute("currentPage", pageNo);
      model.addAttribute("blogDTOList", blogDTOPage);
      model.addAttribute("totalElements", blogDTOPage.getTotalElements());
      model.addAttribute("keyword", keyword);
      if (blogDTOPage.getTotalElements() == 0) {
        model.addAttribute("message", Message.KEYWORD_NOT_FOUND + keyword);
      }
    } else {
      return "redirect:/";
    }
    return "pages/user/search-result";
  }

  @GetMapping("/{blog-path}")
  private String viewBlogDetails(@PathVariable("blog-path") String path, Model model) {
    BlogDTO blogDTO = blogService.findBlogByPath(path, RoleCommon.UN_KNOW);
    numOfAccess++;
    // first time access
    if (numOfAccess == 1) {
      prevBlogDTO = blogDTO;
      model.addAttribute("prevBlogDTO", null);
    }
    if (numOfAccess > 1) {
      //present blog different previous blog
      if (!prevBlogDTO.getPath().equals(path)) {
        model.addAttribute("prevBlogDTO", prevBlogDTO);
        prev2BlogDTO = prevBlogDTO;
        prevBlogDTO = blogDTO;
        // user reload -> present blog equals previous blog
      } else {
        if (!prev2BlogDTO.getPath().equals(path)) {
          model.addAttribute("prevBlogDTO", prev2BlogDTO);
        }
      }
    }
    model.addAttribute("blogDTO", blogDTO);
    return "pages/user/blog-details";
  }


}