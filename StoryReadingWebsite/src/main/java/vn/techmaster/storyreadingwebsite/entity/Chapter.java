package vn.techmaster.storyreadingwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import vn.techmaster.storyreadingwebsite.utils.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "chapter")
@Table(name = "chapter")
@Data
@NoArgsConstructor
public class Chapter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "updateDate", length = 19)
    private Date updateDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "createDate", length = 19)
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private Story story;

    public Chapter(String title, String content, Story story) {
        this.title = title;
        this.content = content;
        this.story = story;
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


}
