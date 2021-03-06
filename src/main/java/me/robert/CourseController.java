package me.robert;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {
    private StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private CourseRepository courseRepository;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {

        this.courseRepository = courseRepository;
    }

    @RequestMapping(value = "/courses/{id}/addstudent", method = RequestMethod.POST)
    public void addStudentToCourse(@PathVariable String id, @Valid String studentId) {
        Optional<Student> studentOptional = studentRepository.findById(new ObjectId(studentId));
        Optional<Course> courseOptional = courseRepository.findById(new ObjectId(id));

        courseOptional.ifPresent(course -> {
            studentOptional.ifPresent(student -> {
                course.getStudents().add(student);
                student.getCourses().add(course);

                studentRepository.save(student);
            });
            courseRepository.save(course);
        });
    }

    @RequestMapping(value = "/courses/{id}/removestudent", method = RequestMethod.POST)
    public void removeStudentFromCourse(@PathVariable String id, @Valid String studentId) {
        Optional<Student> studentOptional = studentRepository.findById(new ObjectId(studentId));
        Optional<Course> courseOptional = courseRepository.findById(new ObjectId(id));

        courseOptional.ifPresent(course -> {
            studentOptional.ifPresent(student -> {
                course.getStudents().remove(student);
                student.getCourses().remove(course);

                studentRepository.save(student);
            });
            courseRepository.save(course);
        });
    }
}