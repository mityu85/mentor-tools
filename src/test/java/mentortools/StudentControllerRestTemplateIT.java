package mentortools;

import mentortools.student.CreateStudentCommand;
import mentortools.student.StudentDto;
import mentortools.student.UpdateStudentCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from students")
public class StudentControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Test
    public void testListStudents() {
        StudentDto studentDto =
                template.postForObject("/api/students",
                    new CreateStudentCommand("John Doe", "john.doe@gmail.com", "john_d", "first test student"),
                        StudentDto.class);
        assertEquals("John Doe", studentDto.getName());

        template.postForObject("/api/students",
                new CreateStudentCommand("Jane Doe", "jane.doe@gmail.com", "jane_d", "second test student"),
                StudentDto.class);

        List<StudentDto> students = template.exchange("/api/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDto>>() {})
                .getBody();
        assertThat(students)
                .extracting(StudentDto::getName)
                .containsExactly("John Doe", "Jane Doe");
    }

    @Test
    public void testListStudentByName() {
        template.postForObject("/api/students",
                new CreateStudentCommand("John Doe", "john.doe@gmail.com", "john_d", "first test student"),
                StudentDto.class);
        template.postForObject("/api/students",
                new CreateStudentCommand("Jane Doe", "jane.doe@gmail.com", "jane_d", "second test student"),
                StudentDto.class);
        List<StudentDto> students = template.exchange("/api/students?name=john doe",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDto>>() {})
                .getBody();

        assertEquals("John Doe", Objects.requireNonNull(students).get(0).getName());
    }

    @Test
    public void testListStudentById() {
        StudentDto studentDto =
                template.postForObject("/api/students",
                    new CreateStudentCommand("John Doe", "john.doe@gmail.com", "john_d", "first test student"),
                    StudentDto.class);

        Long id = studentDto.getId();
        StudentDto anotherStudent = template.exchange("/api/students/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<StudentDto>() {})
                .getBody();

        assertEquals("John Doe", anotherStudent.getName());
    }

    @Test
    public void testUpdateStudent() {
        StudentDto studentDto =
                template.postForObject("/api/students",
                        new CreateStudentCommand("John Doe", "john.doe@gmail.com", "john_d", "first test student"),
                        StudentDto.class);

        Long id = studentDto.getId();
        template.put("/api/students/" + id,
                new UpdateStudentCommand("Jane Doe", "jane.doe@gmail.com", "jane_d", "second test student"),
                StudentDto.class);

        StudentDto anotherStudent = template.exchange("/api/students/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<StudentDto>() {})
                .getBody();

        assertEquals("Jane Doe", anotherStudent.getName());
    }
}
