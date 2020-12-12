package com.leyou.item.web;

import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.rmi.CORBA.Util;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(
            @RequestParam(value = "pid", defaultValue = "0") Long pid
    ) {
        List<Category> list = categoryService.queryListByParent(pid);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("selectByBrandId")
    public ResponseEntity<List<Category>> queryCategoryByBrandId(
            @RequestParam(value = "bid", required = true) Long bid
    ) {
        List<Category> categories = categoryService.queryCategoryByBrandId(bid);
        if (CollectionUtils.isEmpty(categories)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(categories);
    }
}
