package com.majeed.students.controller;

import com.majeed.students.model.Student;
import com.majeed.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/getStudents")
    public List<Student> getStudents() {
     return studentRepository.findAll();
    }


}
