package dev.boot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(name = "U_email", columnNames = "email")
)
@Getter
@Setter
@SequenceGenerator(name = "STUDENT_ID_GEN", sequenceName = "STUDENT_ID_SEQ", allocationSize = 1)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDENT_ID_GEN")
    private long id;

    @Column(nullable = false)
    @NotBlank
    private String fullName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    public Student(){

    }
    public Student(long id, String fullName, LocalDate birthDate, String email) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
    }


}
