package vn.techmaster.storyreadingwebsite.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.techmaster.storyreadingwebsite.entity.Category;
import vn.techmaster.storyreadingwebsite.exception.NotFoundException;
import vn.techmaster.storyreadingwebsite.repository.CategoryRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepo;

    //Lấy danh sách thể loại
    @GetMapping("/categories")
    public String showCategoryList(Model model){
        List<Category> listCategories = categoryRepo.findAll();
        model.addAttribute("listCategories",listCategories);
        return "categories";
    }


    //Thêm thể loại
    @GetMapping("/category/new")
    public String showCreateNewCategoryFrom(Model model){
        model.addAttribute("category", new Category());
        return "category_form";
    }

    @PostMapping("/category/save")
    public String saveCategory(@Valid Category category){
        categoryRepo.save(category);
        return "redirect:/admin/categories";
    }


    // Sửa thể loại
    @GetMapping("/category/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model){
        Category category = categoryRepo.findById(id).get();
        if (id == null) {
            throw new NotFoundException("Id thể loại:" + id + "không tồn tại");
        }
        model.addAttribute("category", category);
        return "category_form";
    }


    //Xóa thể loại
    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id){
        categoryRepo.deleteById(id);
        return "redirect:/admin/categories";
    }
}
