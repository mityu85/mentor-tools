package mentortools;

import mentortools.registration.CreateRegistrationCommand;
import mentortools.registration.RegistrationDto;
import mentortools.student.*;
import mentortools.trainingclass.CreateTrainingClassCommand;
import mentortools.trainingclass.TrainingClassController;
import mentortools.trainingclass.TrainingClassDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from registrations")
@Sql(statements = "delete from students")
@Sql(statements = "delete from training_classes")
public class RegistrationControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    TrainingClassController trainingClassController;

    @Autowired
    StudentController studentController;

    @BeforeEach
    public void init() {
        TrainingClassDto basic = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand("Backend Java Junior",
                        LocalDate.now(),
                        LocalDate.of(2021, 8, 29)), TrainingClassDto.class);
        TrainingClassDto advanced = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand("Backend Java Advanced",
                        LocalDate.now(),
                        LocalDate.of(2021, 12, 29)), TrainingClassDto.class);

        StudentDto john = template.postForObject("/api/students",
                new CreateStudentCommand("John Doe",
                        "john.doe@gmail.com",
                        "john_d",
                        "first test student"), StudentDto.class);
        StudentDto jane = template.postForObject("/api/students",
                new CreateStudentCommand("Jane Doe",
                        "jane.doe@gmail.com",
                        "jane_d",
                        "second test student"), StudentDto.class);

        Long basicId = basic.getId();
        Long advancedId = advanced.getId();
        Long johnId = john.getId();
        Long janeId = jane.getId();

        template.postForObject("/api/trainingclasses/" + basicId + "/registrations",
                new CreateRegistrationCommand(johnId), RegistrationDto.class);

        template.postForObject("/api/trainingclasses/" + basicId + "/registrations",
                new CreateRegistrationCommand(janeId), RegistrationDto.class);

        template.postForObject("/api/trainingclasses/" + advancedId + "/registrations",
                new CreateRegistrationCommand(janeId), RegistrationDto.class);
    }

    @Test
    public void testListRegisteredStudentsByTrainingClassId() {

    }
}
