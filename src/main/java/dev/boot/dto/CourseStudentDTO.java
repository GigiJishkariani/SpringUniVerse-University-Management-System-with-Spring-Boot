package dev.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.boot.domain.Course;
import dev.boot.domain.CourseStudent;
import dev.boot.domain.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Validated
@Schema(title = "CourseStudent", description = "Information about the course student registration")
public class CourseStudentDTO {

    @Valid
    private final CourseStudent courseStudent;

    public CourseStudentDTO() {
        this(new CourseStudent());
    }

    public CourseStudentDTO(CourseStudent courseStudent) {
        this.courseStudent = courseStudent;
    }

    public CourseStudent toCourseStudent() {
        return this.courseStudent;
    }

    @Schema(description = "ID of the course student registration")
    public Long getId() {
        return courseStudent.getId();
    }

    @Schema(hidden = true)
    @JsonIgnore
    @NotNull(message = "Student must be specified")
    public Student getStudent() {
        return courseStudent.getStudent();
    }

    @Schema(hidden = true)
    @JsonIgnore
    @NotNull(message = "Course must be specified")
    public Course getCourse() {
        return courseStudent.getCourse();
    }

    @Schema(description = "ID of the associated course")
    public Long getCourseId() {
        return courseStudent.getCourse() != null ? courseStudent.getCourse().getId() : null;
    }

    @Schema(description = "ID of the associated student")
    public Long getStudentId() {
        return courseStudent.getStudent() != null ? courseStudent.getStudent().getId() : null;
    }

    @Schema(description = "Registration timestamp")
    public LocalDateTime getRegisteredAt() {
        return courseStudent.getRegisteredAt();
    }

    @Schema(description = "Registration count for the course student")
    public int getRegistrationCount() {
        return courseStudent.getRegistrationCount();
    }

    @Schema(description = "Grade for the course student")
    public int getGrade() {
        return courseStudent.getGrade();
    }


    public void setCourseId(Long courseId) {
        if (courseStudent.getCourse() == null) {
            courseStudent.setCourse(new Course());
        }
        courseStudent.getCourse().setId(courseId);
    }

    public void setStudentId(Long studentId) {
        if (courseStudent.getStudent() == null) {
            courseStudent.setStudent(new Student());
        }
        courseStudent.getStudent().setId(studentId);
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        courseStudent.setRegisteredAt(registeredAt);
    }

    public void setRegistrationCount(int registrationCount) {
        courseStudent.setRegistrationCount(registrationCount);
    }

    public void setStudent(Student student) {
        courseStudent.setStudent(student);
    }

    public void setCourse(Course course) {
        courseStudent.setCourse(course);
    }

    public void setGrade(int grade) {
        courseStudent.setGrade(grade);
    }

}
