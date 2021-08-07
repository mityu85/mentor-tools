package mentortools;

import mentortools.trainingclass.CreateTrainingClassCommand;
import mentortools.trainingclass.TrainingClassDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from training_classes")
public class TrainingClassControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Test
    public void testListTrainingClasses() {

        TrainingClassDto trainingClassDto =
                template.postForObject("/api/mentor-tools",
                        new CreateTrainingClassCommand("Backed Java Junior", LocalDate.now(), LocalDate.of(2021, 8, 29)),
                                TrainingClassDto.class);
        assertEquals("Backed Java Junior", trainingClassDto.getName());

        template.postForObject("/api/mentor-tools",
                new CreateTrainingClassCommand("Backed Java Advanced", LocalDate.now(), LocalDate.of(2021, 8, 29)),
                TrainingClassDto.class);

        List<TrainingClassDto> trainingClasses = template.exchange("/api/mentor-tools",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassDto>>() {})
                .getBody();

        assertThat(trainingClasses)
                .extracting(TrainingClassDto::getName)
                .containsExactly("Backed Java Junior", "Backed Java Advanced");
    }
}
