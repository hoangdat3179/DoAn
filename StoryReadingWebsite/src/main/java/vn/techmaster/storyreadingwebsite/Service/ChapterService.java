package vn.techmaster.storyreadingwebsite.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.techmaster.storyreadingwebsite.entity.Chapter;
import vn.techmaster.storyreadingwebsite.repository.ChapterRepository;


@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    /*Lấy danh sách chapter và phân trang*/
    public Page<Chapter> listAll(int pageNum) {
        Sort sort = Sort.by("id").ascending();

        Pageable pageable = PageRequest.of(pageNum - 1, 1, sort);
        return chapterRepository.findByPage(pageable);
    }


}
