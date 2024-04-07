package dev.boot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Course_Student")
@Getter
@Setter
public class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @NotNull
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student")
    @NotNull
    private Student student;

    @Column(name = "student_grade")
    @Min(0)
    @Max(100)
    int grade;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "registration_count")
    @Min(1)
    private int registrationCount;
}
