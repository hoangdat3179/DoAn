package vn.techmaster.storyreadingwebsite.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.storyreadingwebsite.Service.StoryService;
import vn.techmaster.storyreadingwebsite.entity.Category;
import vn.techmaster.storyreadingwebsite.entity.Story;
import vn.techmaster.storyreadingwebsite.repository.CategoryRepository;
import vn.techmaster.storyreadingwebsite.repository.ChapterRepository;
import vn.techmaster.storyreadingwebsite.repository.StoryRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminStoryController {

    @Autowired
    private StoryRepository storyRepo;
    @Autowired
    private StoryService storyService;
    @Autowired
    private ChapterRepository chapterRepo;

    @Autowired
    private CategoryRepository categoryRepo;


    //Lấy danh sách truyện
    @GetMapping("/stories")
    public String showBookList(Model model){
        return listByPage(model,1,"");
    }


    //Tạo mới truyện
    @GetMapping("/story/new")
    public String showCreateNewBookFrom(Model model){
        List<Category> listCategories = categoryRepo.findAll();
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("story", new Story());
        return "add_story";
    }

    @PostMapping("/story/save")
    public String saveBook(@RequestParam("fileImage") MultipartFile multipartFile, Story story,
                           Model model,@Param("keyword") String keyword) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        story.setImage(fileName);
        Story savedStory = storyRepo.save(story);

        String uploadDir = "book-images/" + savedStory.getId();

        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream()){;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream ,filePath , StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Could not save uploaded file: " + fileName);
        }

        return listByPage(model,1,keyword);
    }


    // Sửa truyện
    @GetMapping("/story/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model){
        Story story = storyRepo.findById(id).get();
        List<Category> listCategories = categoryRepo.findAll();
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("story", story);
        return "add_story";
    }


    //Xóa truyện
    @GetMapping("/story/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        storyRepo.deleteById(id);
        return "redirect:/admin/stories";
    }


    //Tìm truyện
    @GetMapping("/search")
    public String search(Model model,@Param("keyword") String keyword){
        return listByPage(model,1,keyword);
    }


    //Phân trang
    @GetMapping("/story/page/{pageNumer}")
    public String listByPage(Model model,
                           @PathVariable(name = "pageNumer") int currentPage,
                            @Param("keyword") String keyword){
        Page<Story> page = storyService.listAll(currentPage,keyword);

        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Story> listStories = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("listStories", listStories);
        model.addAttribute("keyword", keyword);
        return "stories";
    }

}
