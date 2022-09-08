package vn.techmaster.storyreadingwebsite;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vn.techmaster.storyreadingwebsite.entity.Category;
import vn.techmaster.storyreadingwebsite.repository.CategoryRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class StoryRepositoryTests {

    @Autowired
    private CategoryRepository repo;

    @Test
    public void testCreateCategories() {
        Category category = new Category("Việt Nam");
        Category category1 = new Category("Khác");
        Category category2 = new Category("Chiến Tranh");


        repo.saveAll(List.of(category, category1, category2));

        List<Category> listCategories = repo.findAll();

        assertThat(listCategories.size()).isEqualTo(3);
    }
}
