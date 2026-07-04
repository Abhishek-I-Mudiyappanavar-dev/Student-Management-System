package com.app.Student_Management_System.service.serviceImpl;

import com.app.Student_Management_System.dto.request.StudentRequest;
import com.app.Student_Management_System.dto.response.StudentResponse;
import com.app.Student_Management_System.entity.Department;
import com.app.Student_Management_System.entity.Student;
import com.app.Student_Management_System.exception.DepartmentNotFoundException;
import com.app.Student_Management_System.exception.DuplicateEmailException;
import com.app.Student_Management_System.exception.StudentNotFoundException;
import com.app.Student_Management_System.mapper.StudentMapper;
import com.app.Student_Management_System.repository.DepartmentRepository;
import com.app.Student_Management_System.repository.StudentRepository;
import com.app.Student_Management_System.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final DepartmentRepository departmentRepository;

    private final StudentMapper studentMapper;


    @Override
    public StudentResponse admitStudent(StudentRequest request) {

        if(studentRepository.findByEmail(request.getEmail()).isPresent()){
            throw new DuplicateEmailException("Email already exists: "+request.getEmail());
        }
        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()-> new DepartmentNotFoundException("There is no department with id: "+request.getDepartmentId()));
        Student student = studentMapper.toEntity(request,department);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse updateStudentInfo(String id, StudentRequest request) {

        Student enrolledStudent = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException("There is no student with id: "+id));

        studentRepository.findByEmail(request.getEmail())
                .ifPresent(student -> {
                    if (!enrolledStudent.getId().equals(student.getId())) {
                        throw new DuplicateEmailException(
                                "Email already associated with other student: " + request.getEmail()
                        );
                    }
                });

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()-> new DepartmentNotFoundException("There is no department with id: "+request.getDepartmentId()));

        enrolledStudent.setFirstName(request.getFirstName());
        enrolledStudent.setLastName(request.getLastName());
        enrolledStudent.setEmail(request.getEmail());
        enrolledStudent.setGender(request.getGender());
        enrolledStudent.setPhoneNumber(request.getPhoneNumber());
        enrolledStudent.setDateOfBirth(request.getDateOfBirth());
        enrolledStudent.setDepartment(department);

        Student updatedStudent = studentRepository.save(enrolledStudent);

        return studentMapper.toResponse(updatedStudent);
    }

    @Override
    public void deleteStudentById(String id) {

        Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException("There is no student with id: "+id));
        studentRepository.delete(student);
    }

    @Override
    public List<StudentResponse> getStudentsByDepartmentId(String departmentId) {
        List<Student> students = studentRepository.findByDepartmentId(departmentId);

        return studentMapper.toResponseList(students);
    }

    @Override
    public StudentResponse getStudentById(String id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("No student found with id: "+id));
        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()-> new StudentNotFoundException("No student found with email: "+email));
        return studentMapper.toResponse(student);
    }

    @Override
    public List<StudentResponse> searchStudents(String firstName, String lastName) {

        List<Student> students;

        if(firstName != null && lastName != null){
            students = studentRepository
                    .findByFirstNameAndLastName(firstName, lastName);
        }
        else if (firstName != null) {
            students = studentRepository.findByFirstName(firstName);
        }
        else if(lastName != null){
            students = studentRepository.findByLastName(lastName);
        }
        else {
            students = studentRepository.findAll();
        }

        return studentMapper.toResponseList(students);
    }

    @Override
    public Integer getEarnedCredits(String id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("No student found with id: "+id));
        return student.getEarnedCredits();
    }
}
