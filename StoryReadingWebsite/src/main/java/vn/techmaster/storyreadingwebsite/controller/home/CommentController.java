package vn.techmaster.storyreadingwebsite.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.techmaster.storyreadingwebsite.Service.StoryService;
import vn.techmaster.storyreadingwebsite.entity.Comment;
import vn.techmaster.storyreadingwebsite.entity.Story;
import vn.techmaster.storyreadingwebsite.exception.NotFoundException;
import vn.techmaster.storyreadingwebsite.repository.CommentRepository;
import vn.techmaster.storyreadingwebsite.repository.StoryRepository;

import javax.validation.Valid;

@Controller
public class CommentController {
    @Autowired
    private StoryService storyService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private StoryRepository storyRepo;

    // Thêm comment vào truyện

    @PostMapping("/story/{sId}")
    public String saveChapter(@Valid Comment comment, @PathVariable("sId") Long id){
        Story storyOptional = storyService.findById(id).get();
        comment.setStory(storyOptional);
        commentRepository.save(comment);
        return "redirect:/story/" + storyOptional.getId();
    }

    //Xóa Comment
    @GetMapping("/stories/{sID}/comment/delete/{id}")
    public String deleteChapter(@PathVariable("id") Long id,@PathVariable("sID")Long sId){
        Story story = storyRepo.findById(sId).get();
        commentRepository.deleteById(id);
        return "redirect:/story/" + story.getId();
    }

}
