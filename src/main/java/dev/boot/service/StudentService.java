package dev.boot.service;

import dev.boot.domain.Student;
import dev.boot.dto.StudentDTO;
import dev.boot.repository.StudentRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public StudentService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    public StudentDTO save(StudentDTO studentDTO) {
        Student save = studentRepo.save(studentDTO.toStudent());
        return new StudentDTO(save);
    }


    public StudentDTO update(long id, StudentDTO studentDTO) {
        Optional<StudentDTO> existingStudent = findById(id);

        if (existingStudent.isPresent()) {
            Student existingEntity = existingStudent.get().toStudent();
            existingEntity.setFullName(studentDTO.getFullName());
            existingEntity.setBirthDate(studentDTO.getBirthDate());
            existingEntity.setEmail(studentDTO.getEmail());

            return new StudentDTO(studentRepo.save(existingEntity));
        }

        studentDTO.setId(id);
        return save(studentDTO);
    }

    private void validateOnUpdate(StudentDTO studentDTO) {
        Collection<String> validationMessages = new HashSet<>();
        if (StringUtils.isBlank(studentDTO.getFullName())) {
            validationMessages.add(" FullName can not be blank");
        }
        if (StringUtils.isBlank(studentDTO.getEmail())) {
            validationMessages.add("Email must be valid");
        }

        if (Objects.isNull(studentDTO.getBirthDate())) {
            validationMessages.add("Birth date can't be null");
        }


        if (validationMessages.isEmpty()) {
            return;
        }
        throw new ValidationException(Strings.join(validationMessages.iterator(), ';'));
    }



    public Optional<StudentDTO> findById(long id) {
        return studentRepo.findById(id).map(StudentDTO::new);
    }

    public Iterable<StudentDTO> findAll() {
        return StreamSupport.stream(studentRepo.findAll().spliterator(), false)
                .map(StudentDTO::new)
                .collect(Collectors.toSet());
    }

    public void delete(StudentDTO entity) {
        studentRepo.delete(entity.toStudent());
    }

    public List<StudentDTO> findStudentsByGrade(String gradeCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = cb.createQuery(Student.class);
        Root<Student> studentRoot = query.from(Student.class);

        Predicate gradePredicate = createGradePredicate(cb, studentRoot, gradeCriteria);

        query.select(studentRoot).where(gradePredicate);

        List<Student> students = entityManager.createQuery(query).getResultList();

        return students.stream().map(StudentDTO::new).collect(Collectors.toList());
    }

    private Predicate createGradePredicate(CriteriaBuilder cb, Root<Student> studentRoot, String gradeCriteria) {
        Predicate gradePredicate;

        if (gradeCriteria.equalsIgnoreCase("A")) {
            gradePredicate = cb.between(studentRoot.get("grade"), 91, 100);
        } else if (gradeCriteria.equalsIgnoreCase("B")) {
            gradePredicate = cb.between(studentRoot.get("grade"), 81, 90);
        } else if (gradeCriteria.equalsIgnoreCase("C")) {
            gradePredicate = cb.between(studentRoot.get("grade"), 71, 80);
        } else {
            gradePredicate = cb.conjunction();
        }

        return gradePredicate;
    }



}



