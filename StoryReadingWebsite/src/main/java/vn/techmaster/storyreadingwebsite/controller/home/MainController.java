package vn.techmaster.storyreadingwebsite.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.techmaster.storyreadingwebsite.Service.ChapterService;
import vn.techmaster.storyreadingwebsite.Service.StoryService;
import vn.techmaster.storyreadingwebsite.entity.Comment;
import vn.techmaster.storyreadingwebsite.entity.Story;
import vn.techmaster.storyreadingwebsite.entity.Category;
import vn.techmaster.storyreadingwebsite.entity.Chapter;
import vn.techmaster.storyreadingwebsite.exception.NotFoundException;
import vn.techmaster.storyreadingwebsite.repository.CommentRepository;
import vn.techmaster.storyreadingwebsite.repository.StoryRepository;
import vn.techmaster.storyreadingwebsite.repository.CategoryRepository;
import vn.techmaster.storyreadingwebsite.repository.ChapterRepository;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private StoryRepository storyRepo;
    @Autowired
    private StoryService storyService;
    @Autowired
    private ChapterRepository chapterRepo;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private CommentRepository commentRepository;

    // Lấy danh sách tất cả truyện
    @GetMapping("/")
    public String home(Model model){
        List<Category> listCategories = categoryRepo.findAll();
        List<Story> listStories = storyRepo.findAll();
        List<Story> storyFull = storyService.findByStatus();
        model.addAttribute("storyFull",storyFull);
        model.addAttribute("listStories",listStories);
        model.addAttribute("listCategories",listCategories);
        return "index";
    }

    // Lấy truyện theo ID và comment
    @GetMapping(value = "/story/{id}")
    public String showBookDetailByID(Model model, @PathVariable("id") Long id) {
        //Tìm id truyện
        Story story = storyRepo.findById(id).get();
        List<Category> listCategories = categoryRepo.findAll();

        //Liệt kê chapter theo id truyện
        List<Chapter> listChapters = chapterRepo.findByStoryId(id);

        //Liệt kê bình luận theo id truyện
        List<Comment> commentList = commentRepository.findByStoryId(id);

        //Ném ra lỗi nếu truyện không tồn tại
        if (id == null) {
            throw new NotFoundException("Id truyện:" + id + "không tồn tại");
        }

        // Gọi model ra html (sử dụng thymeleaf)
        model.addAttribute("comment",new Comment());
        model.addAttribute("listChapters",listChapters);
        model.addAttribute("commentList",commentList);
        model.addAttribute("story", story);
        model.addAttribute("listCategories",listCategories);
        return "bookDetail";
    }

    //Lấy chapter của truyện
    @GetMapping(value = "/story/{sId}/chapter/{chId}")
    public String showChapter(Model model,
                              @PathVariable("sId") Long storyId,
                              @PathVariable("chId") Long chapterId) {

        // Tìm nội dung chương truyện theo id truyện và id chương
        Optional<Chapter> chapter = chapterRepo.findChapterByStoryIdAndChapterId(storyId,chapterId);

        // Liệt kê danh sách thể loại
        List<Category> listCategories = categoryRepo.findAll();

        // Lấy id truyện
        Story story = storyRepo.findById(storyId).get();

        // Liệt kê chương theo truyện
        List<Chapter> listChapters = chapterRepo.findByStoryId(storyId);

        // Ném ra lỗi nếu truyện không tồn tại
        if(storyId == null){
            throw new NotFoundException("Id truyện:" + storyId + "không tồn tại");
        }

        listByPage(model,1,storyId,chapterId);
        model.addAttribute("chapter", chapter.get());
        model.addAttribute("listChapters",listChapters);
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("story", story);
        return "chapterBook";
    }


    // Tìm kiếm Truyện
    @RequestMapping("/search")
    public String searchByKeyword(Model model, String keyword){
        List<Category> listCategories = categoryRepo.findAll();
        if(keyword!=null){
            List<Story> listStories = storyService.findByTitleContainsIgnoreCase(keyword);
            model.addAttribute("listStories", listStories);
        }else {
            List<Story> listStories = storyRepo.findAll();
            model.addAttribute("listStories", listStories);
        }
        model.addAttribute("listCategories",listCategories);

        return "bookCategory";
    }


    // Tìm truyện theo thể loại
    @RequestMapping("/story/category/{id}")
    public String findBookByCategory(Model model,@PathVariable("id") Long categoryId){
        List<Category> listCategories = categoryRepo.findAll();
        List<Story> listStories = storyRepo.findByCategoriesId(categoryId);
        if(categoryId == null){
            throw new NotFoundException("Thể loại không tồn tại");
        }
        model.addAttribute("listStories", listStories);
        model.addAttribute("listCategories",listCategories);
        return "bookCategory";
    }

    // Tìm chapter trước và sau sử dụng paging
    @GetMapping("/story/{sId}/chapter/{chId}/page/{pageNumer}")
    public String listByPage(Model model,
                             @PathVariable(name = "pageNumer") int currentPage,
                             @PathVariable("sId") Long storyId,
                             @PathVariable("chId") Long chapterId){

        //Lấy danh sách chương sử dụng phân trang theo số trang hiện tại (Page)
        Page<Chapter> page = chapterService.listAll(currentPage);

        // Tìm nội dung chương truyện theo id truyện và id chương
        Optional<Chapter> chapter = chapterRepo.findChapterByStoryIdAndChapterId(storyId,chapterId);

        //Liệt kê danh sách thể loại
        List<Category> listCategories = categoryRepo.findAll();

        //Lấy truyện theo id
        Story story = storyRepo.findById(storyId).get();

        //Lấy danh sách chương theo id truyện
        List<Chapter> listChapters = chapterRepo.findByStoryId(storyId);
        if(storyId == null){
            throw new NotFoundException("Id truyện:" + storyId + "không tồn tại");
        }

        // lấy danh sách chương theo paging
        List<Chapter> listChapter = page.getContent();

        model.addAttribute("chapter", chapter.get());
        model.addAttribute("listChapters",listChapters);
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("story", story);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("listChapter", listChapter);
        return "chapterPaging";
    }

}
