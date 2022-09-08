package vn.techmaster.storyreadingwebsite.controller.admin;

import lombok.extern.log4j.Log4j2;
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
import vn.techmaster.storyreadingwebsite.exception.NotFoundException;
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
@Log4j2
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


    //Form thêm truyện
    @GetMapping("/story/new")
    public String showCreateNewBookFrom(Model model){
        //Lấy danh sách thể loại
        List<Category> listCategories = categoryRepo.findAll();
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("story", new Story());
        return "add_story";
    }


    // Thêm truyện
    @PostMapping("/story/save")
    public String saveBook(@RequestParam("fileImage") MultipartFile multipartFile, Story story,
                           Model model,@Param("keyword") String keyword) throws IOException {

        // upload ảnh vào truyện
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
            throw new IOException("Không thể upload file: " + fileName);
        }

        return listByPage(model,1,keyword);
    }


    // Sửa truyện
    @GetMapping("/story/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model){
        // Lấy truyện theo id
        Story story = storyRepo.findById(id).get();
        if (id == null) {
            throw new NotFoundException("Id truyện:" + id + "không tồn tại");
        }
        // Lấy danh sách thể loại
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

        // Lấy danh sách truyện theo phân trang và tìm kiếm theo keyword
        Page<Story> page = storyService.listAll(currentPage,keyword);

        // Lấy tổng số truyện
        long totalItems = page.getTotalElements();

        //Lấy tổng số trang
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
