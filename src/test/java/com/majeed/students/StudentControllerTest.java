package com.majeed.students.controller;

import com.majeed.students.model.Student;
import com.majeed.students.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setAge(20);

        student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setAge(22);
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() throws Exception {
        List<Student> allStudents = Arrays.asList(student1, student2);

        when(studentRepository.findAll()).thenReturn(allStudents);

        mockMvc.perform(get("/api/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student1.getId()))
                .andExpect(jsonPath("$[0].firstName").value(student1.getFirstName()))
                .andExpect(jsonPath("$[1].id").value(student2.getId()))
                .andExpect(jsonPath("$[1].firstName").value(student2.getFirstName()));

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_ShouldReturnStudent() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));

        mockMvc.perform(get("/api/students/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student1.getId()))
                .andExpect(jsonPath("$.firstName").value(student1.getFirstName()));

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void getStudentById_ShouldReturnNotFound() throws Exception {
        when(studentRepository.findById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/students/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(studentRepository, times(1)).findById(3L);
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() throws Exception {
        Student newStudent = new Student();
        newStudent.setFirstName("Peter");
        newStudent.setLastName("Jones");
        newStudent.setAge(21);

        Student savedStudent = new Student();
        savedStudent.setId(3L);
        savedStudent.setFirstName("Peter");
        savedStudent.setLastName("Jones");
        savedStudent.setAge(21);

        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Peter\",\"lastName\":\"Jones\",\"age\":21}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.firstName").value(savedStudent.getFirstName()));

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() throws Exception {
        Student updatedStudentDetails = new Student();
        updatedStudentDetails.setFirstName("Jonathan");
        updatedStudentDetails.setLastName("Davis");
        updatedStudentDetails.setAge(25);

        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setFirstName("Jonathan");
        updatedStudent.setLastName("Davis");
        updatedStudent.setAge(25);


        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/api/students/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Jonathan\",\"lastName\":\"Davis\",\"age\":25}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedStudent.getId()))
                .andExpect(jsonPath("$.firstName").value(updatedStudent.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedStudent.getLastName()))
                .andExpect(jsonPath("$.age").value(updatedStudent.getAge()));

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_ShouldReturnNotFound() throws Exception {
        when(studentRepository.findById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/students/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Jonathan\",\"lastName\":\"Davis\",\"age\":25}"))
                .andExpect(status().isNotFound());

        verify(studentRepository, times(1)).findById(3L);
        verify(studentRepository, times(0)).save(any(Student.class));
    }

    @Test
    void deleteStudent_ShouldReturnNoContent() throws Exception {
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        mockMvc.perform(delete("/api/students/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudent_ShouldReturnNotFound() throws Exception {
        when(studentRepository.existsById(3L)).thenReturn(false);

        mockMvc.perform(delete("/api/students/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(studentRepository, times(1)).existsById(3L);
        verify(studentRepository, times(0)).deleteById(anyLong());
    }
}