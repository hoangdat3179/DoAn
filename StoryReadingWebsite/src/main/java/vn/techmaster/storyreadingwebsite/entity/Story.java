package vn.techmaster.storyreadingwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import vn.techmaster.storyreadingwebsite.utils.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "story")
@Table(name = "story")
@Data
@NoArgsConstructor
public class Story implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String title;

    @Column(nullable = false)
    private String author;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true)
    private String link;
    @Column(nullable = true)
    private String image;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "story",cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Chapter> chapters = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "story_category",
            joinColumns = @JoinColumn(name = "story_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "updateDate", length = 19)
    private Date updateDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "createDate", length = 19)
    private Date createDate;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,fetch = FetchType.LAZY
    )
    @JoinColumn(name = "story_id", referencedColumnName = "id")
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setStory(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setStory(null);
    }

    public Story(String title, String author, String description, String image, Status status, List<Category> categories) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.image = image;
        this.status = status;
        this.categories = categories;
    }

    @PrePersist
    public void prePersist() {
        if (createDate == null) {
            createDate = DateUtils.getCurrentDate();
        }
        if (updateDate == null) {
            updateDate = DateUtils.getCurrentDate();
        }
    }

    @Transient
    public String getImagePath(){
        if(image == null || id ==null) return null;
        return "/book-images/" + id + "/" + image;
    }

    public void addCategory(Category category){
        this.categories.add(category);
    }
    public void removeCategory(Category category){
       this.categories.remove(category);
    }
}
