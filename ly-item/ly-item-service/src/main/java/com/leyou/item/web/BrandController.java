package com.leyou.item.web;

import com.github.pagehelper.Page;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>>  queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Brand> result = brandService.queryBrandByPageAndSort(page, rows, sortBy, desc, key);
        if (result == null || result.getItems().size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("save")
    public ResponseEntity<Void> insertCategoryBrand(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "letter", required = true) Character letter,
            @RequestParam(value = "cids", required = true) List<Long> cids
    ) {
        brandService.insertCategoryBrand(name, letter, cids);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 更新商品信息
     * TODO: 更新关联表
     * @param id
     * @param name
     * @param letter
     * @return
     */
    @PostMapping("update")
    public ResponseEntity<Integer> updateBrand(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "letter", required = true) Character letter
//            @RequestParam(value = "cids", required = false) List<Long> cids
    ) {
        int updatedId = brandService.updateBrand(id, name, letter);
//        if (updatedId == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        return ResponseEntity.ok(updatedId);
    }

    @PostMapping("delete")
    public ResponseEntity<Void> deleteBrand(
            @RequestBody Brand brand
    ) {
        brandService.deleteBrand(brand.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
