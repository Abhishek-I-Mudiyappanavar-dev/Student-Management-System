package com.app.Student_Management_System.service.serviceImpl;

import com.app.Student_Management_System.dto.request.StudentRequest;
import com.app.Student_Management_System.dto.request.StudentUpdateRequest;
import com.app.Student_Management_System.dto.response.PageResponse;
import com.app.Student_Management_System.dto.response.StudentResponse;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.entity.Student;
import com.app.Student_Management_System.exception.DepartmentNotFoundException;
import com.app.Student_Management_System.exception.DuplicateEmailException;
import com.app.Student_Management_System.exception.StudentNotFoundException;
import com.app.Student_Management_System.mapper.PageResponseMapper;
import com.app.Student_Management_System.mapper.StudentMapper;
import com.app.Student_Management_System.repository.DepartmentRepository;
import com.app.Student_Management_System.repository.StudentRepository;
import com.app.Student_Management_System.service.StudentService;
import com.app.Student_Management_System.specification.StudentSpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final DepartmentRepository departmentRepository;

    private final StudentMapper studentMapper;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);


    @Override
    public StudentResponse admitStudent(StudentRequest request) {

        logger.info("Student admission process started for email: '{}'", request.getEmail());

        if(studentRepository.findByEmail(request.getEmail()).isPresent()){
            logger.warn("Student admission rejected: email '{}' already exists.", request.getEmail());
            throw new DuplicateEmailException("Email already exists: "+request.getEmail());
        }

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()-> {
            logger.warn("Student admission rejected: no department with id:'{}'", request.getDepartmentId());
            return new DepartmentNotFoundException("No department found with id: "+request.getDepartmentId());
        });

        Student student = studentMapper.toEntity(request,department);
        StudentResponse response = studentMapper.toResponse(studentRepository.save(student));
        logger.info("Student created successfully with id '{}' and email '{}'  ",student.getId(), student.getEmail());
        return response;
    }

    @Override
    public StudentResponse updateStudent(String id, StudentUpdateRequest request){
        logger.info("Processing update request for student with id '{}'",id);

        Student enrolledStudent = studentRepository.findById(id).orElseThrow(()-> {
            logger.warn("No student found with id '{}'", id);
            return new StudentNotFoundException("There is no student with id: "+id);
        });

        if(request.getEmail()!=null && !Objects.equals(enrolledStudent.getEmail(), request.getEmail())){
            studentRepository.findByEmail(request.getEmail())
                    .ifPresent(student ->{
                        if(!enrolledStudent.getId().equals(student.getId())){
                            logger.warn("Email already associated with other student: '{}'",request.getEmail());
                            throw new DuplicateEmailException("Email already associated with other student: "+request.getEmail());
                        }
                    });
            enrolledStudent.setEmail(request.getEmail());
        }

        if(request.getFirstName()!=null) enrolledStudent.setFirstName(request.getFirstName());
        if(request.getLastName()!=null) enrolledStudent.setLastName(request.getLastName());
        if(request.getGender()!=null) enrolledStudent.setGender(request.getGender());
        if(request.getPhoneNumber()!=null) enrolledStudent.setPhoneNumber(request.getPhoneNumber());

        StudentResponse updatedStudent = studentMapper.toResponse(studentRepository.save(enrolledStudent));
        logger.info("Student updated successfully with id '{}'",updatedStudent.getId());
        return updatedStudent;

    }

    @Override
    public void deleteStudent(String id) {

        logger.info("Processing delete request for student with id '{}'", id);

        Student student = studentRepository.findById(id).orElseThrow(()->{
            logger.warn("No student found with id '{}'", id);
            return new StudentNotFoundException("There is no student with id: "+id);
        });
        studentRepository.delete(student);
        logger.info("Student deleted successfully with id '{}'",id);
    }

    @Override
    public StudentResponse getStudentById(String id) {
        Student student = studentRepository.findById(id).orElseThrow(() ->{
            logger.warn("No student found with id '{}'", id);
            return new StudentNotFoundException("No student found with id: "+id);
        });
        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()-> {
            logger.warn("No student found with email '{}'",email);
            return new StudentNotFoundException("No student found with email: "+email);
        });
        return studentMapper.toResponse(student);
    }

    @Override
    public PageResponse<StudentResponse> searchStudents(String firstName, String lastName, String departmentId, Pageable pageable) {

        Page<Student> students;

        Specification<Student> specification = Specification.allOf(
                StudentSpecification.hasFirstName(firstName),
                StudentSpecification.hasLastName(lastName),
                StudentSpecification.hasDepartmentId(departmentId));
        students = studentRepository.findAll(specification, pageable);

        List<StudentResponse> responses = studentMapper.toResponseList(students.getContent());

        return PageResponseMapper.toPageResponse(students,responses);
    }

    @Override
    public Integer getStudentEarnedCredits(String id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> {
            logger.warn("No student found with id '{}'", id);
            return new StudentNotFoundException("No student found with id: "+id);
        });
        return student.getEarnedCredits();
    }
}
