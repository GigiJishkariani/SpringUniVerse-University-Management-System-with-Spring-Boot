package dev.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.boot.domain.Course;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Schema(title = "Course", description = "Information about the course of university")
public class CourseDTO {

    private final Course course;

    public CourseDTO(){
        this(new Course());
    }

    public CourseDTO(Course course){
        this.course = course;
    }

    public Course toCourse(){
        return this.course;
    }

    @NotBlank(message = "Course name is required")
    @Size(max = 255, message = "Course name should not exceed 255 characters")
    @Schema(description = "Name of the course")
    public String getCourseName() {
        return course.getCourseName();
    }

    @NotBlank(message = "Course code is required")
    @Size(max = 10, message = "Course code should not exceed 10 characters")
    @Schema(description = "Code of the course")
    public String getCourseCode() {
        return course.getCourseCode();
    }

    @Schema(description = "ID of the course")
    public long getId() {
        return course.getId();
    }


    @NotNull(message = "End date is required")
    @Schema(description = "End date of the course")
    public LocalDate getStartDate(){
        return course.getStartDate();
    }

    @Schema(description = "End date of the course")
    public LocalDate getEndDate(){
        return course.getEndDate();
    }

    @Schema(description = "Max number of registrations on the course")
    public int getMaxRegistrations(){
        return course.getMaxRegistrations();
    }


    @Schema(description = "Registration allowed before this time on the course")
    public int getTimeBeforeStart(){
        return course.getTimeBeforeStart();
    }

    @JsonIgnore
    @Schema(description = "Registration allowed before this time on the course, integer")
    public Duration getTimeBeforeStartDuration(){
        return Duration.ofDays(course.getTimeBeforeStart());
    }

    @Schema(description = "Registration allowed after this time on the course")
    public int getTimeAfterStart(){
        return course.getTimeAfterStart();
    }

    @JsonIgnore
    @Schema(description = "Registration allowed after this time on the course, int")
    public Duration getTimeAfterStartDuration(){
        return Duration.ofDays(course.getTimeAfterStart());
    }


    public void setCourseCode(String courseCode){
        course.setCourseCode(courseCode);
    }

    @Schema(hidden = true)
    public void setId(long id){
        course.setId(id);
    }

    public void setCourseName(String courseName) {
        course.setCourseName(courseName);
    }

    public void setStartDate(LocalDate startDate) {
        course.setStartDate(startDate);
    }

    public void setEndDate(LocalDate endDate) {
        course.setEndDate(endDate);
    }

    public void setMaxRegistrations(int number) {
        course.setMaxRegistrations(number);
    }

    public void setTimeBeforeStart(int timeBeforeStart) {
        course.setTimeBeforeStart(timeBeforeStart);
    }

    public void setTimeAfterStart(int timeAfterStart) {
        course.setTimeAfterStart(timeAfterStart);
    }


}
