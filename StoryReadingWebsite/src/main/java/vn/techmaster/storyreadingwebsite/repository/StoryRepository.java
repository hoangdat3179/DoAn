package vn.techmaster.storyreadingwebsite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.techmaster.storyreadingwebsite.entity.Status;
import vn.techmaster.storyreadingwebsite.entity.Story;

import java.util.List;
import java.util.Optional;


@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    // Tìm truyện và phân trang
    @Query("select s from story s")
    Page<Story> findAllByPage(String keyword, Pageable pageable);

    // Tìm kiếm truyện theo tên
    @Query("select s from story s where upper(s.title) like upper(concat('%', ?1, '%'))")
    List<Story> findByTitleContainsIgnoreCase(String title);

    // Tìm truyện theo ID
    Optional<Story> findById(Long id);

    // Tìm truyện theo trạng thái
    @Query("select s from story s where s.status = ?1")
    List<Story> findByStatus(Status status);

    // Tìm truyện theo thể loại
    @Query("select s from story s inner join s.categories categories where categories.id = ?1")
    List<Story> findByCategoriesId(Long id);


}
