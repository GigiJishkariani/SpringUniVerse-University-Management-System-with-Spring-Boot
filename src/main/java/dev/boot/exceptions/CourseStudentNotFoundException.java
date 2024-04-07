package dev.boot.exceptions;

public class CourseStudentNotFoundException extends RuntimeException{
    public CourseStudentNotFoundException(String message) {
        super(message);
    }

}
