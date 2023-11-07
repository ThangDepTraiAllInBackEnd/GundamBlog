package com.blog.admincontroller;

import com.blog.common.Constant;
import com.blog.common.Message;
import com.blog.common.RoleCommon;
import com.blog.dto.BlogDTO;
import com.blog.dto.SeriesDTO;
import com.blog.dto.TagDTO;
import com.blog.models.Tag;
import com.blog.repositories.SeriesRepository;
import com.blog.repositories.TagRepository;
import com.blog.services.BlogService;
import com.blog.services.SeriesService;
import com.blog.services.TagService;
import com.blog.wrapper.TagDTOWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
  @Autowired
  BlogService blogService;
  @Autowired
  SeriesService seriesService;
  @Autowired
  TagService tagService;

  @Autowired
  TagRepository tagRepository;

  @Autowired
  SeriesRepository seriesRepository;

  @GetMapping("/")
  private String showAdminHomePage() {
    return "pages/admin/home";
//    return "pages/admin/create-blog";
  }

  //==============Blog=================
  @GetMapping("/create-blog")
  private String showCreateBlogPage(Model model) {
    model.addAttribute("blogCreationDTO", new BlogDTO());
    model.addAttribute("tagDTOWrapper", new TagDTOWrapper());
    model.addAttribute("seriesDTO", new SeriesDTO());
    model.addAttribute("seriesDTOList", seriesService.findAllSeries());
    model.addAttribute("tagsDTO", tagService.findAllTag());
    return "pages/admin/create-blog";
  }

  @PostMapping("/save-blog")
  private String saveBlog(@ModelAttribute("blogCreationDTO") BlogDTO blogDTO,
                          @ModelAttribute("tagDTOWrapper") TagDTOWrapper tagDTOWrapper,
                          @ModelAttribute("seriesDTO") SeriesDTO seriesDTO) {
    blogService.createBlog(blogDTO, tagDTOWrapper, seriesDTO);
    return "redirect:/admin/create-blog";
  }

  @GetMapping("/update-{blog-path}")
  private String showUpdateBlogPage(@PathVariable("blog-path") String path, Model model, HttpSession session) {
    BlogDTO blogDTO = blogService.findBlogByPath(path, RoleCommon.ADMIN);
    SeriesDTO seriesDTO = new SeriesDTO();
    TagDTOWrapper tagDTOWrapper = new TagDTOWrapper();
    List<String> tagsName = blogDTO.getTags().stream()
        .map(Tag::getTagName)
        .collect(Collectors.toList());
    tagDTOWrapper.setTagsName(tagsName);
    seriesDTO.setSeriesName(blogDTO.getSeries().getSeriesName());
    model.addAttribute("blogCreationDTO", blogDTO);
    model.addAttribute("tagDTOWrapper", tagDTOWrapper);
    model.addAttribute("seriesDTO", seriesDTO);
    model.addAttribute("seriesDTOList", seriesService.findAllSeries());
    model.addAttribute("tagsDTO", tagService.findAllTag());
    session.setAttribute("path", blogDTO.getPath());
    session.setAttribute("date", blogDTO.getDate());
    session.setAttribute("backgroundImage", blogDTO.getBackgroundImage());
    return "pages/admin/update-blog";
  }

  @PostMapping("/update-blog")
  private String updateBlog(@ModelAttribute("blogCreationDTO") BlogDTO blogDTO,
                            @ModelAttribute("tagDTOWrapper") TagDTOWrapper tagDTOWrapper,
                            @ModelAttribute("seriesDTO") SeriesDTO seriesDTO,
                            HttpSession session) {
    System.out.println("controller roi");
    blogDTO.setPath((String) session.getAttribute("path"));
    blogDTO.setDate((Date) session.getAttribute("date"));
    blogDTO.setBackgroundImage((String) session.getAttribute("backgroundImage"));
    blogService.updateBlog(blogDTO, tagDTOWrapper, seriesDTO);
    return "redirect:/admin/create-blog";
  }

  @GetMapping("/manage-blog")
  private String showManageBlogsPage(Model model, @RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
    Page<BlogDTO> blogDTOPage;
    if (keyword != null && keyword != "") {
      blogDTOPage = blogService.findBlogByTitle(keyword, pageNo, Constant.SEARCH_PAGE_SIZE, RoleCommon.ADMIN);
      if (blogDTOPage.getTotalElements() == 0) {
        model.addAttribute("message", Message.KEYWORD_NOT_FOUND + keyword);
      }
    } else {
      blogDTOPage = blogService.pagingAllBlog(pageNo, Constant.PAGE_SIZE, blogService.findAllBlog(RoleCommon.ADMIN));
    }
    model.addAttribute("totalPage", blogDTOPage.getTotalPages());
    model.addAttribute("blogDTOList", blogDTOPage);
    model.addAttribute("totalElements", blogDTOPage.getTotalElements());
    model.addAttribute("keyword", keyword);
    model.addAttribute("currentPage", pageNo);
    return "pages/admin/manage-blogs";
  }
  //==============End blog=================

  //==============Series=================
  @GetMapping("/create-series")
  private String showCreateSeriesPage(Model model) {
    model.addAttribute("seriesDTO", new SeriesDTO());
    return "pages/admin/create-series";
  }

  @PostMapping("/save-series")
  private String saveSeries(@ModelAttribute("seriesDTO") SeriesDTO seriesDTO) {
    System.out.println("Controller" + seriesDTO.toString());
    seriesService.createSeries(seriesDTO);
    return "redirect:/admin/create-series";
  }

  @GetMapping("/manage-series")
  private String showManageSeriesPage(Model model, @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
    Page<SeriesDTO> seriesPage;
    if (keyword != null && keyword != "") {
      seriesPage = seriesService.findSeriesByTitleForAdmin(keyword, pageNo, Constant.SEARCH_PAGE_SIZE);
      if (seriesPage.getTotalElements() == 0) {
        model.addAttribute("message", Message.KEYWORD_NOT_FOUND + keyword);
      }
    } else {
      seriesPage = seriesService.findSeriesByTitleForAdmin("", pageNo, Constant.SEARCH_PAGE_SIZE);
    }
    model.addAttribute("totalPage", seriesPage.getTotalPages());
    model.addAttribute("seriesList", seriesPage);
    model.addAttribute("totalElements", seriesPage.getTotalElements());
    model.addAttribute("keyword", keyword);
    model.addAttribute("currentPage", pageNo);
    return "pages/admin/manage-series";
  }

  @GetMapping("/blog-in-series-{seriesId}")
  private String showBlogInSeriesPage(@PathVariable("seriesId") Long seriesId,
                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      Model model) {
    Page<BlogDTO> blogDTOPage = blogService.getBlogBySeriesId(seriesId, pageNo, Constant.SEARCH_PAGE_SIZE, RoleCommon.ADMIN);
    model.addAttribute("seriesName", seriesRepository.findById(seriesId).get().getSeriesName());
    model.addAttribute("totalPage", blogDTOPage.getTotalPages());
    model.addAttribute("blogDTOList", blogDTOPage);
    model.addAttribute("totalElements", blogDTOPage.getTotalElements());
    model.addAttribute("seriesId", seriesId);
    model.addAttribute("currentPage", pageNo);
    return "pages/admin/blog-in-series";
  }

  @GetMapping("/renameseries")
  private String reNameSeries(Model model, @RequestParam(value = "tagId", required = false) Long tagId,
                              @RequestParam(value = "newSeriesName", required = false) String newSeriesName) {
    //update tag name
    if (newSeriesName != null && newSeriesName != "") {
      seriesService.renameSeries(tagId, newSeriesName);
    }
    return "redirect:/admin/manage-series";
  }

  //==============End series=================
  //==============Tag=================
  @GetMapping("/create-tag")
  private String showCreateTagPage(Model model) {
    model.addAttribute("tagDTO", new TagDTO());
    return "pages/admin/create-tag";
  }

  @PostMapping("/save-tag")
  private String saveTag(@ModelAttribute("tagDTO") TagDTO tagDTO) {
    tagService.createTag(tagDTO);
    return "pages/admin/create-tag";
  }

  @GetMapping("/manage-tag")
  private String showManageTagPage(Model model, @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
    Page<TagDTO> tagPage;
    if (keyword != null && keyword != "") {
      tagPage = tagService.findTagByTitleForAdmin(keyword, pageNo, Constant.SEARCH_PAGE_SIZE);

      if (tagPage.getTotalElements() == 0) {
        model.addAttribute("message", Message.KEYWORD_NOT_FOUND + keyword);
      }
    } else {
      tagPage = tagService.findTagByTitleForAdmin("", pageNo, Constant.SEARCH_PAGE_SIZE);
    }
    model.addAttribute("totalPage", tagPage.getTotalPages());
    model.addAttribute("tagList", tagPage);
    model.addAttribute("totalElements", tagPage.getTotalElements());
    model.addAttribute("keyword", keyword);
    model.addAttribute("currentPage", pageNo);
    return "pages/admin/manage-tag";
  }

  @GetMapping("/renametag")
  private String reNameTag(Model model, @RequestParam(value = "tagId", required = false) Long tagId,
                           @RequestParam(value = "newTagName", required = false) String newTagName) {
    //update tag name
    if (newTagName != null && newTagName != "") {
      tagService.renameTag(tagId, newTagName);
    }
    return "redirect:/admin/manage-tag";
  }

  @GetMapping("/blog-in-tag-{tagId}")
  private String showBlogInTagPage(@PathVariable("tagId") Long tagId,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   Model model) {
    Page<BlogDTO> blogDTOPage = blogService.getBlogByTagId(tagId, pageNo, Constant.SEARCH_PAGE_SIZE, RoleCommon.ADMIN);
    model.addAttribute("tagName", tagRepository.findById(tagId).get().getTagName());
    model.addAttribute("totalPage", blogDTOPage.getTotalPages());
    model.addAttribute("blogDTOList", blogDTOPage);
    model.addAttribute("totalElements", blogDTOPage.getTotalElements());
    model.addAttribute("tagId", tagId);
    model.addAttribute("currentPage", pageNo);
    return "pages/admin/blog-in-tag";
  }
  //==============End tag=================
}
