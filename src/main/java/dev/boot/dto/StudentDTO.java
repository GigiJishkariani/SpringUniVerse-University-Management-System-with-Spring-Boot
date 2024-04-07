package dev.boot.dto;


import dev.boot.domain.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Schema(title = "Student", description = "Information about the Student of university")
public class StudentDTO {
    private final Student student;

    public StudentDTO(){
        this(new Student());
    }

    public StudentDTO(Student student){
        this.student = student;
    }

    public Student toStudent(){
        return this.student;
    }

    @NotBlank(message = "Full name is required")
    @Size(max = 255, message = "Full name should not exceed 255 characters")
    @Schema(description = "Full name of the student")
    public String getFullName() {
        return student.getFullName();
    }

    @Schema(description = "studentId")
    public long getId(){
        return student.getId();
    }

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @Schema(description = "Birth date of the student")
    public LocalDate getBirthDate(){
        return student.getBirthDate();
    }

    @NotBlank(message = "Email is required")
    @Size(max = 255, message = "Email should not exceed 255 characters")
    @Schema(description = "Email address of the student")
    public String getEmail() {
        return student.getEmail();
    }

    public void setFullName(String fullName) {
        student.setFullName(fullName);
    }

    @Schema(hidden = true)
    public void setId(long id) {
        student.setId(id);
    }

    public void setEmail(String newMail) {
        student.setEmail(newMail);
    }

    public void setBirthDate(LocalDate day) {
        student.setBirthDate(day);
    }



}
