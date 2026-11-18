
package com.kiet.hostelmanagement.controller;

import com.kiet.hostelmanagement.entity.HostelStudent;
import com.kiet.hostelmanagement.repository.HostelStudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/students")
public class HostelStudentController {

    @Autowired
    private HostelStudentRepository repository;

    // 1. Add Student
    @PostMapping
    public HostelStudent addStudent(@RequestBody HostelStudent student) {
        return repository.save(student);
    }

    // 2. Get All Students
    @GetMapping
    public List<HostelStudent> getAllStudents() {
        return repository.findAll();
    }

    // 3. Get Student By ID
    @GetMapping("/{id}")
    public ResponseEntity<HostelStudent> getStudentById(
            @PathVariable int id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Update Student
    @PutMapping("/{id}")
    public ResponseEntity<HostelStudent> updateStudent(
            @PathVariable int id,
            @RequestBody HostelStudent updatedStudent) {

        return repository.findById(id).map(student -> {

            student.setHostelName(updatedStudent.getHostelName());
            student.setRoomNumber(updatedStudent.getRoomNumber());
            student.setFee(updatedStudent.getFee());

            return ResponseEntity.ok(repository.save(student));

        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable int id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.ok("Student deleted successfully");
    }
}
