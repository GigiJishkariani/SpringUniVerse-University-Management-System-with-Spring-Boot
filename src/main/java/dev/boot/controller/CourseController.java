package dev.boot.controller;


import dev.boot.dto.CourseDTO;
import dev.boot.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {
    private final CourseService service;

    @Autowired
    public CourseController(CourseService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds course by id")
    public Optional<CourseDTO> findById(@PathVariable(name = "id") long id) {
        return service.findById(id);
    }

    @GetMapping
    @Operation(summary = "Finds all courses")
    public Iterable<CourseDTO> findAll() {
        return service.findAll();
    }


    @PostMapping
    @Validated
    @Operation(summary = "Saves course",
    description = "This endpoint adds new course to university database. " +
    "Course information cannot be empty",
    responses = {
            @ApiResponse(responseCode = "201",description = "course added successfully"),
            @ApiResponse(responseCode = "400",description = "course data is null or empty")
    })
    public ResponseEntity<CourseDTO> save(@RequestBody @Valid CourseDTO courseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(courseDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes the course by id")
    public void delete(@PathVariable(name = "id") long courseId){
        findById(courseId).ifPresent(service::delete);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates the course by id")
    public CourseDTO update(@PathVariable(name = "id") long id, @RequestBody CourseDTO courseDTO) {
        return service.update(id, courseDTO);
    }

}
