package vn.techmaster.storyreadingwebsite.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.techmaster.storyreadingwebsite.entity.Status;
import vn.techmaster.storyreadingwebsite.entity.Story;
import vn.techmaster.storyreadingwebsite.repository.StoryRepository;
import vn.techmaster.storyreadingwebsite.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;


    @Autowired
    UserRepository userRepo;




    /*Lấy danh sách truyện và phân trang*/
    public Page<Story> listAll(int pageNum, String keyword) {
        Sort sort = Sort.by("id").ascending();

        Pageable pageable = PageRequest.of(pageNum - 1, 3, sort);
        if (keyword != null) {
            return storyRepository.findAllByPage(keyword, pageable);
        }
        return storyRepository.findAll(pageable);
    }

    // Tìm truyện theo ID
    public Optional<Story> findById(Long id){
        return storyRepository.findById(id);
    }


  /*  Tìm kiếm theo tên*/
    public List<Story> findByTitleContainsIgnoreCase(String title) {
        List<Story> stories = storyRepository.findByTitleContainsIgnoreCase(title);
        return stories;
    }

    // Tìm truyện theo trạng thái
    public List<Story> findByStatus(){
        List<Story> stories = storyRepository.findByStatus(Status.HOANTHANH);
        return stories;
    }

}
