package dev.boot.service;

import dev.boot.domain.Course;
import dev.boot.domain.CourseStudent;
import dev.boot.domain.Student;
import dev.boot.repository.CourseRepository;
import dev.boot.repository.CourseStudentRepository;
import dev.boot.repository.StudentRepository;
import dev.boot.dto.CourseStudentDTO;
import dev.boot.exceptions.CourseStudentNotFoundException;
import dev.boot.exceptions.RegistrationLimitExceededException;
import dev.boot.exceptions.RegistrationNotAllowedException;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RegistrationService {


    private CourseStudentRepository courseStudentRepo;
    private StudentRepository studentRepo;
    private CourseRepository courseRepo;

    public RegistrationService(CourseStudentRepository courseStudentRepo,StudentRepository studentRepo,CourseRepository courseRepo){
        this.courseStudentRepo = courseStudentRepo;
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }


    public Optional<CourseStudentDTO> findById(long id) {
        return courseStudentRepo.findById(id).map(CourseStudentDTO::new);
    }

    public Iterable<CourseStudentDTO> findAll() {
        return StreamSupport.stream(courseStudentRepo.findAll().spliterator(), false)
                .map(CourseStudentDTO::new)
                .collect(Collectors.toSet());
    }

    public void delete(CourseStudentDTO entity) {
        courseStudentRepo.delete(entity.toCourseStudent());

    }


    public CourseStudentDTO register(long studentId, long courseId){

        Student student = studentRepo.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));

//        if (!isRegistrationAllowed(course)) {
//            throw new RegistrationNotAllowedException("Registration is not allowed at this time.");
//        }
        if (hasAlreadyPassed(student,course)){
            throw new RegistrationNotAllowedException("Student has already passed this course");
        }

        if (isRegistrationLimitExceeded(course, student)) {
            throw new RegistrationLimitExceededException("Student has reached the maximum registration limit for this course.");
        }

        int regCount = (int) courseStudentRepo.findByStudentIdAndCourseId(studentId,courseId).spliterator().estimateSize();

        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setStudent(student);
        courseStudent.setCourse(course);
        courseStudent.setRegisteredAt(LocalDateTime.now());
        courseStudent.setRegistrationCount(regCount + 1);

        CourseStudent savedCourseStudent = courseStudentRepo.save(courseStudent);
        return new CourseStudentDTO(savedCourseStudent);

    }


    private boolean isRegistrationLimitExceeded(Course course, Student student) {
        int maxRegistrations = course.getMaxRegistrations();
        long registrationCount = courseStudentRepo.countByCourseAndStudent(course, student);
        return registrationCount >= maxRegistrations;
    }

    private boolean isRegistrationAllowed(Course course) {
        LocalDate courseStartDate = course.getStartDate();
        Duration timeBeforeStart = Duration.ofDays(course.getTimeBeforeStart());
        Duration timeAfterStart = Duration.ofDays(course.getTimeAfterStart());
        LocalDateTime currentTime = LocalDateTime.now();

        LocalDate registrationStart = courseStartDate.minus(timeBeforeStart);
        LocalDate registrationEnd = courseStartDate.plus(timeAfterStart);

        return currentTime.toLocalDate().isAfter(registrationStart) && currentTime.toLocalDate().isBefore(registrationEnd);
    }

    private boolean hasAlreadyPassed(Student student, Course course){
        Iterable<CourseStudent> existingRegistrations = courseStudentRepo.findByStudentIdAndCourseId(student.getId(), course.getId());
        for (CourseStudent registration : existingRegistrations){
            if (registration.getGrade() >= 51){
                return true;
            }
        }
        return false;

    }

    public CourseStudentDTO updateScore(long studentId, long courseId, int score) {

        Optional<CourseStudentDTO> courseStudentDTO =
                StreamSupport.stream(findAll().spliterator(), false)
                        .filter(courseStudentDTO1 ->
                                courseStudentDTO1.getCourseId() == courseId
                        &&
                                courseStudentDTO1.getStudentId() == studentId)
                        .findFirst();

        if (courseStudentDTO.isPresent()) {
            CourseStudent courseStudent = courseStudentDTO.get().toCourseStudent();
            courseStudent.setGrade(score);

            return new CourseStudentDTO(
                    courseStudentRepo.save(courseStudent)
            );


        } else {
            throw new CourseStudentNotFoundException("Course Student not found.");
        }
    }

}
