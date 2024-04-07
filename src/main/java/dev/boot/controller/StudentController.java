package dev.boot.controller;


import dev.boot.dto.StudentDTO;
import dev.boot.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds student by id")
    public Optional<StudentDTO> findById(@PathVariable(name = "id") long id) {
        return studentService.findById(id);
    }
    @GetMapping("/find-by-grade")
    @Operation(summary = "Finds students by grade")
    public List<StudentDTO> findStudentsByGrade(@RequestParam String grade) {
        return studentService.findStudentsByGrade(grade);
    }

    @GetMapping
    @Operation(summary = "Finds all students")
    public Iterable<StudentDTO> findAll() {
        return studentService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates the student by id")
    public StudentDTO update(@PathVariable(name = "id") long id, @RequestBody StudentDTO studentDTO) {
        return studentService.update(id, studentDTO);
    }

    @PostMapping
    @Validated
    @Operation(summary = "Saves Student",
            description = "This endpoint adds new student to university database. " +
                    "Student information cannot be empty" +
                    "email must be valid" +
                    "birth date must be valid (Past date)",

            responses = {
                    @ApiResponse(responseCode = "201",description = "student added successfully"),
                    @ApiResponse(responseCode = "400",description = "inputted student data is invalid")
            })
    public ResponseEntity<StudentDTO> save(@RequestBody @Valid StudentDTO studentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.save(studentDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes student by id")
    public void delete(@PathVariable(name = "id") long studentId){
        findById(studentId).ifPresent(studentService::delete);
    }
}
