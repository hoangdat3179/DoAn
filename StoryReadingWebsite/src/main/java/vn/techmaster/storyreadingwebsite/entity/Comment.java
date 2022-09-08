package vn.techmaster.storyreadingwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity(name = "comments")
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Không được để trống")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "Không được để trống")
    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;
    private LocalDateTime lastUpdate;

    @PrePersist //Trước khi lưu khi khởi tạo record
    public void prePersist() {
        lastUpdate = LocalDateTime.now();
    }
    @PreUpdate //Khi cập nhật record
    public void preUpdate() {
        lastUpdate = LocalDateTime.now();
    }

    public Comment(String content) {
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Story story; //Mỗi comment phải gắn vào một story

}
