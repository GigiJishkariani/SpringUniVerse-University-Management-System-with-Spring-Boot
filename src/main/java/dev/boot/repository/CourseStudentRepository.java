package dev.boot.repository;

import dev.boot.domain.Course;
import dev.boot.domain.CourseStudent;
import dev.boot.domain.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseStudentRepository  extends CrudRepository<CourseStudent, Long> {
    long countByCourseAndStudent(Course course, Student student);
    Iterable<CourseStudent> findByStudentIdAndCourseId(long studentId, long CourseId);
}
