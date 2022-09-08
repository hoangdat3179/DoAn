package vn.techmaster.storyreadingwebsite.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.techmaster.storyreadingwebsite.Service.ChapterService;
import vn.techmaster.storyreadingwebsite.Service.StoryService;
import vn.techmaster.storyreadingwebsite.entity.Chapter;
import vn.techmaster.storyreadingwebsite.entity.Story;
import vn.techmaster.storyreadingwebsite.repository.ChapterRepository;
import vn.techmaster.storyreadingwebsite.repository.StoryRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminChapterController{
    @Autowired
    private StoryService storyService;

    @Autowired
    private StoryRepository storyRepo;
    @Autowired
    private ChapterRepository chapterRepo;


    //Lấy chương theo id
    @GetMapping(value = "/story/{id}")
    public String showBookDetailByID(Model model,@PathVariable("id") Long id) {
        Story story = storyRepo.findById(id).get();
        List<Chapter> listChapters = chapterRepo.findByStoryId(id);
        model.addAttribute("listChapters",listChapters);
        model.addAttribute("story", story);
        return "book_detail";
    }

    //Thêm chương theo truyện id
    @PostMapping("/story/add/chapter/{id}")
    public String saveChapter(Chapter chapter, @PathVariable("id") Long id){
        Story storyOptional = storyService.findById(id).get();
        chapter.setStory(storyOptional);
        chapterRepo.save(chapter);
        return "redirect:/admin/story/" + storyOptional.getId();
    }

    @GetMapping(value = "/story/add/chapter/{id}")
    public String addForm(Model model,@PathVariable("id") Long id) {
        Optional<Story> storyOptional = storyRepo.findById(id);
        model.addAttribute("story",storyOptional.get());
        model.addAttribute("chapter",new Chapter());
        return "add_chapter";
    }


    //Sửa chương
    @GetMapping("/story/{sID}/chapter/edit/{chID}")
    public String showEditChapterForm(@PathVariable("chID") Long chID, Model model,@PathVariable("sID")Long sID){
        Story storyOptional = storyService.findById(sID).get();
        Chapter chapter = chapterRepo.findById(chID).get();
        model.addAttribute("story", storyOptional);
        model.addAttribute("chapter", chapter);
        return "add_chapter";
    }


    //Xóa chương
    @GetMapping("/story/{sID}/chapter/delete/{id}")
    public String deleteChapter(@PathVariable("id") Long id,@PathVariable("sID")Long sId){
        Story story = storyRepo.findById(sId).get();
        chapterRepo.deleteById(id);
        return "redirect:/admin/story/" + story.getId();
    }

}
