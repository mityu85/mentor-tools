package mentortools.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDto> listStudents(@RequestParam Optional<String> name) {
        return studentService.listStudents(name);
    }

    @GetMapping("/{id}")
    public StudentDto listStudentById(@PathVariable("id") Long id) {
        return studentService.listStudentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto createStudent(@Valid @RequestBody CreateStudentCommand command) {
        return studentService.createStudent(command);
    }

    @PutMapping("/{id}")
    public StudentDto updateStudent(@PathVariable("id") Long id, @Valid @RequestBody UpdateStudentCommand command) {
        return studentService.updateStudent(id, command);
    }
}
