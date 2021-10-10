package com.student.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.student.dao.StudentRepository;
import com.student.model.Student;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> addStudent(@RequestBody Student student) {
		Student stud = studentRepository.save(student);
		return new ResponseEntity<>(stud, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/list/all", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> list() {
		List<Student> students = studentRepository.findAll();
		if (students.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
	}

	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findStudent(@PathVariable int id) {
		Student stud = studentRepository.findById(id).orElse(null);
		if (stud != null)
			return new ResponseEntity<Student>(stud, HttpStatus.OK);
		else
			return new ResponseEntity<>(("Student with id " + id + " not found"), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteStudent(@PathVariable int id) {
		Student stud = studentRepository.findById(id).orElse(null);
		if (stud != null) {
			studentRepository.deleteById(id);
			return new ResponseEntity<>("Student with id " + id + " deleted succesfully", HttpStatus.OK);
		} else
			return new ResponseEntity<>(("Student with id " + id + " not found"), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
	public ResponseEntity<Student> deleteAll() {
		studentRepository.deleteAll();
		return new ResponseEntity<Student>(HttpStatus.ACCEPTED);
	}

}
