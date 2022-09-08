package vn.techmaster.storyreadingwebsite;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(value = false)
public class CategoryRepositoryTests {
}
