package mentortools.student;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentService {

    private ModelMapper modelMapper;
    private StudentRepository repository;

    public List<StudentDto> listStudents(Optional<String> name) {
        return repository.findAll().stream()
                .filter(s -> name.isEmpty() || s.getName().equalsIgnoreCase(name.get()))
                .map(s -> modelMapper.map(s, StudentDto.class))
                .collect(Collectors.toList());
    }

    public StudentDto createStudent(CreateStudentCommand command) {
        return modelMapper.map(repository.save(new Student(
                command.getName(),
                command.getEmail(),
                command.getGitHubUserName(),
                command.getDesc()
                )), StudentDto.class);
    }

    public StudentDto listStudentById(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id cannot be found " + id));
        return modelMapper.map(student, StudentDto.class);
    }

    public StudentDto updateStudent(Long id, UpdateStudentCommand command) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id cannot be found " + id));
        student.setName(command.getName());
        student.setEmail(command.getEmail());
        student.setGitHubUserName(command.getGitHubUserName());
        student.setDesc(command.getDesc());
        repository.save(student);
        return modelMapper.map(student, StudentDto.class);
    }
}
