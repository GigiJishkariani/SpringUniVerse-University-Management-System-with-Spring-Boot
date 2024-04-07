package dev.boot.service;

import dev.boot.domain.Course;
import dev.boot.dto.CourseDTO;
import dev.boot.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {

    private final CourseRepository courseRepo;

    @Autowired
    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public CourseDTO save(CourseDTO courseDTO) {
        Course save = courseRepo.save(courseDTO.toCourse());
        CourseDTO courseDTO1 = new CourseDTO(save);

        return courseDTO1;
    }


    public CourseDTO update(long id, CourseDTO courseDTO) {
        Optional<CourseDTO> existingCourse = findById(id);

        if (existingCourse.isPresent()) {
            Course existingEntity = existingCourse.get().toCourse();
            existingEntity.setCourseName(courseDTO.getCourseName());
            existingEntity.setCourseCode(courseDTO.getCourseCode());
            existingEntity.setStartDate(courseDTO.getStartDate());
            existingEntity.setEndDate(courseDTO.getEndDate());
            existingEntity.setMaxRegistrations(courseDTO.getMaxRegistrations());
            existingEntity.setTimeBeforeStart(courseDTO.getTimeBeforeStart());
            existingEntity.setTimeAfterStart(courseDTO.getTimeAfterStart());

            return new CourseDTO(courseRepo.save(existingEntity));
        }

        courseDTO.setId(id);
        return save(courseDTO);
    }

    public Optional<CourseDTO> findById(long id) {
        return courseRepo.findById(id).map(CourseDTO::new);
    }

    public Iterable<CourseDTO> findAll() {
        return StreamSupport.stream(courseRepo.findAll().spliterator(), false)
                .map(CourseDTO::new)
                .collect(Collectors.toSet());
    }

    public void delete(CourseDTO entity) {
        courseRepo.delete(entity.toCourse());
    }

}
