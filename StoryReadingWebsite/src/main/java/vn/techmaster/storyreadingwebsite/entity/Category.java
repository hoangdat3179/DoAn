package vn.techmaster.storyreadingwebsite.entity;


import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Objects;

@Entity(name = "category")
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(unique = true, nullable = false, length = 25)
    private String name;


    public Category(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;

    }
}
