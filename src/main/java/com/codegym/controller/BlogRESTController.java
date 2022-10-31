package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.blog.IBlogService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("blog/api")
public class BlogRESTController {
    @Autowired
    ICategoryService categoryService;

    @Autowired
    IBlogService blogService;

    //    Xem danh sách các category
    @GetMapping("/category")
    public ResponseEntity<List<Category>> showCategoryList() {
        List<Category> categoryList = categoryService.findAll();
        if (categoryList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    //    Xem danh sách các bài viết
    @GetMapping("/allBlogList")
    public ResponseEntity<List<Blog>> showAllBlogList() {
        List<Blog> blogList = blogService.findAll();
        if (blogList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(blogList, HttpStatus.OK);
    }

    //    Xem danh sách các bài viết của một category
    @GetMapping("getBlogByCategory/{id}")
    public ResponseEntity<List<Blog>> showBlogByCategory(@PathVariable Long id) {
        List<Blog> blogList = blogService.findAll();
        if (blogList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Blog> resultList = new ArrayList<>();
        for (int i = 0; i < blogList.size(); i++) {
            if (blogList.get(i).getCategory().getId().equals(id)) {
                resultList.add(blogList.get(i));
            }
        }

        if (resultList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    //    Xem chi tiết một bài viết
    @RequestMapping(value = "detailBlog/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Blog> showBlogDetail(@PathVariable Long id) {
        Optional<Blog> blogToShow = blogService.findById(id);
        if (!blogToShow.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogToShow.get(), HttpStatus.OK);
    }

}