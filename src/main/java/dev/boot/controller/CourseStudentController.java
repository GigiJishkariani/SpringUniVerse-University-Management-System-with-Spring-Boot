package dev.boot.controller;


import dev.boot.dto.CourseStudentDTO;
import dev.boot.exceptions.CourseStudentNotFoundException;
import dev.boot.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/course-students")
public class CourseStudentController {
    private final RegistrationService registrationService;

    @Autowired
    public CourseStudentController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping
    @Validated
    @Operation(summary = "Registers a student for a course",
            description = "This endpoint adds new studentcourse to university database. ",
            responses = {
                    @ApiResponse(responseCode = "201",description = "studentCourse added successfully"),
                    @ApiResponse(responseCode = "400",description = "invalid data is inputted")
            })
    public ResponseEntity<CourseStudentDTO> registerForCourse(long studentId, long courseId) {
        CourseStudentDTO save = registrationService.register(studentId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(save);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds course-student by id")
    public Optional<CourseStudentDTO> findById(@PathVariable(name = "id") long id) {
        return registrationService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Finds all course students")
    public Iterable<CourseStudentDTO> findAll() {
        return registrationService.findAll();
    }

    @DeleteMapping("/{studentId}-{courseId}")
    @Operation(summary = "Removes the student from course by ids")
    public void delete(@PathVariable(name = "studentId") long studentId,@PathVariable(name = "courseId") long courseId){
        StreamSupport.stream(findAll().spliterator(), false)
                .forEach(courseStudentDTO -> {
                    if (courseStudentDTO.getStudentId() == studentId && courseStudentDTO.getCourseId() == courseId){
                        registrationService.delete(courseStudentDTO);
                    }
                });

    }


    @PutMapping("/{studentId}-{courseId}/score")
    @Operation(summary = "Updates a student's score for the course",
            description = "This endpoint updates the students' scores from the university database. ",
            responses = {
                    @ApiResponse(responseCode = "201",description = "grade updated successfully")
    })
    public ResponseEntity<CourseStudentDTO> updateScore(
            @PathVariable(name = "studentId") long studentId,
            @PathVariable(name = "courseId") long courseId,
            @RequestParam(name = "score") int score) {

        try {
            CourseStudentDTO updatedCourseStudent = registrationService.updateScore(studentId, courseId, score);
            if (updatedCourseStudent != null) {
                return ResponseEntity.ok(updatedCourseStudent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (CourseStudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
