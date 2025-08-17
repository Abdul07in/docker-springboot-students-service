package com.majeed.students;

import com.majeed.students.model.Student;
import com.majeed.students.repository.StudentRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testSaveStudent() {
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAge(20);

        Student savedStudent = studentRepository.save(student);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getFirstName()).isEqualTo("John");
        assertThat(savedStudent.getLastName()).isEqualTo("Doe");
        assertThat(savedStudent.getAge()).isEqualTo(20);

        Optional<Student> foundStudent = studentRepository.findById(savedStudent.getId());
        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get()).isEqualTo(savedStudent);
    }

    @Test
    void testFindStudentById() {
        Student student = new Student();
        student.setFirstName("Jane");
        student.setLastName("Smith");
        student.setAge(22);
        entityManager.persistAndFlush(student);

        Optional<Student> foundStudent = studentRepository.findById(student.getId());

        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getFirstName()).isEqualTo("Jane");
        assertThat(foundStudent.get().getLastName()).isEqualTo("Smith");
        assertThat(foundStudent.get().getAge()).isEqualTo(22);
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student();
        student.setFirstName("Peter");
        student.setLastName("Jones");
        student.setAge(19);
        entityManager.persistAndFlush(student);

        Student studentToUpdate = studentRepository.findById(student.getId()).get();
        studentToUpdate.setAge(20);
        studentToUpdate.setLastName("Smith");

        Student updatedStudent = studentRepository.save(studentToUpdate);

        assertThat(updatedStudent.getAge()).isEqualTo(20);
        assertThat(updatedStudent.getLastName()).isEqualTo("Smith");
    }

    @Test
    void testDeleteStudent() {
        Student student1 = new Student();
        student1.setFirstName("Alice");
        student1.setLastName("Brown");
        student1.setAge(21);
        entityManager.persistAndFlush(student1);

        Student student2 = new Student();
        student2.setFirstName("Bob");
        student2.setLastName("White");
        student2.setAge(23);
        entityManager.persistAndFlush(student2);

        studentRepository.deleteById(student1.getId());

        Optional<Student> deletedStudent = studentRepository.findById(student1.getId());
        assertThat(deletedStudent).isNotPresent();

        Optional<Student> remainingStudent = studentRepository.findById(student2.getId());
        assertThat(remainingStudent).isPresent();
    }

    @Test
    void testFindAllStudents() {
        Student student1 = new Student();
        student1.setFirstName("Alice");
        student1.setLastName("Brown");
        student1.setAge(21);
        entityManager.persistAndFlush(student1);

        Student student2 = new Student();
        student2.setFirstName("Bob");
        student2.setLastName("White");
        student2.setAge(23);
        entityManager.persistAndFlush(student2);

        Iterable<Student> students = studentRepository.findAll();

        assertThat(students).hasSize(2);
    }
}