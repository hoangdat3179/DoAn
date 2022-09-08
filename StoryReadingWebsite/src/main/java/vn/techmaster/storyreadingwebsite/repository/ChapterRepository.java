package vn.techmaster.storyreadingwebsite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.techmaster.storyreadingwebsite.entity.Chapter;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    //Lấy chương theo ID truyện
    @Query("select c from chapter c where c.story.id = ?1")
    List<Chapter> findByStoryId(Long id);

    //Lấy nội dung chương theo ID truyện và ID chương
    @Query("select c from chapter c where c.story.id = ?1 and c.id = ?2")
    Optional<Chapter> findChapterByStoryIdAndChapterId(Long storyId, Long chapterId);

    @Query("select c from chapter c")
    Page<Chapter> findByPage(Pageable pageable);

}