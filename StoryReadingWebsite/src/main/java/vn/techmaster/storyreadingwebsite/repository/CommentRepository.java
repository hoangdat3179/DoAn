package vn.techmaster.storyreadingwebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.techmaster.storyreadingwebsite.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Lấy danh sách comment theo ID truyện
    @Query("select c from comments c where c.story.id = ?1")
    List<Comment> findByStoryId(Long id);


}
