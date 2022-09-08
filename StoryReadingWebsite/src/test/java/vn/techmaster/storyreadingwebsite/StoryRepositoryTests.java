package vn.techmaster.storyreadingwebsite;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vn.techmaster.storyreadingwebsite.entity.Category;
import vn.techmaster.storyreadingwebsite.entity.Status;
import vn.techmaster.storyreadingwebsite.entity.Story;
import vn.techmaster.storyreadingwebsite.repository.CategoryRepository;
import vn.techmaster.storyreadingwebsite.repository.StoryRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class StoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Test
    public void testCreateCategories() {
        Category category = new Category("Việt Nam");
        Category category1 = new Category("Khác");
        Category category2 = new Category("Chiến Tranh");


        categoryRepository.saveAll(List.of(category, category1, category2));

        List<Category> listCategories = categoryRepository.findAll();

        assertThat(listCategories.size()).isEqualTo(3);
    }

    @Test
    public void testCreateStories() {
        Category category = new Category("Du kích");
        Category category2 = new Category("Viễn Tưởng");


        categoryRepository.saveAll(List.of(category, category2));
        List<Category> listCategories = categoryRepository.findAll();
        Story story = new Story("Cao Thủ","Caothu","Truỵen Kiem Hiep","avatar.jpg", Status.HOANTHANH,listCategories);


        storyRepository.save(story);

        assertThat(story.getId() > 0);
    }
}
