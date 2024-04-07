package dev.boot.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;


@Entity
@Table(name = "course",
        uniqueConstraints = @UniqueConstraint(name = "U_COURSE_CODE", columnNames = "COURSE_CODE")
)
@Getter
@Setter
@SequenceGenerator(name = "COURSE_ID_GEN", sequenceName = "COURSE_ID_SEQ", allocationSize = 1)
public class Course {

    @NotBlank(message = "Course name is required")
    @Column(name = "COURSE_NAME")
    private String courseName;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURSE_ID_GEN")
    private long id;

    @NotBlank(message = "Course code is required")
    @Column(name = "COURSE_CODE")
    private String courseCode;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(name = "end_date")
    private LocalDate endDate;

    @Min(value = 1, message = "Max registrations must be a non-negative value")
    @Column(name = "max_registrations")
    private int maxRegistrations;

    @Min(value = 0)
    @Column(name = "time_before_start")
    private int timeBeforeStart;

    @Min(value = 0)
    @Column(name = "time_after_start")
    private int timeAfterStart;
    //for how many days is it allowed to register after the course has started


}
