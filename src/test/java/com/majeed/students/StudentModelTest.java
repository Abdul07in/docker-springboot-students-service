package com.majeed.students;

import com.majeed.students.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testGettersAndSetters() {
        Student student = new Student();
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        int age = 20;

        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setAge(age);

        assertEquals(id, student.getId());
        assertEquals(firstName, student.getFirstName());
        assertEquals(lastName, student.getLastName());
        assertEquals(age, student.getAge());
    }

    @Test
    void testToString() {
        Student student = new Student(1L, "Jane", "Doe", 22);
        String expectedToString = "Student(id=1, firstName=Jane, lastName=Doe, age=22)";
        assertEquals(expectedToString, student.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Student student1 = new Student(1L, "John", "Doe", 20);
        Student student2 = new Student(1L, "John", "Doe", 20);
        Student student3 = new Student(2L, "Jane", "Smith", 22);

        // Test equality
        assertTrue(student1.equals(student2));
        assertTrue(student2.equals(student1));
        assertEquals(student1.hashCode(), student2.hashCode());

        // Test non-equality
        assertFalse(student1.equals(student3));
        assertFalse(student3.equals(student1));
        assertNotEquals(student1.hashCode(), student3.hashCode());

        // Test equality with null
        assertFalse(student1.equals(null));

        // Test equality with different object type
        assertFalse(student1.equals("some string"));
    }

    @Test
    void testNoArgsConstructor() {
        Student student = new Student();
        assertNull(student.getId());
        assertNull(student.getFirstName());
        assertNull(student.getLastName());
        assertEquals(0, student.getAge());
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        String firstName = "Peter";
        String lastName = "Jones";
        int age = 25;
        Student student = new Student(id, firstName, lastName, age);
        assertEquals(id, student.getId());
        assertEquals(firstName, student.getFirstName());
        assertEquals(lastName, student.getLastName());
        assertEquals(age, student.getAge());
    }
}