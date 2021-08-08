package mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.student.Student;
import mentortools.trainingclass.TrainingClass;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    private Long id;
    private TrainingClass trainingClass;
    private Student student;
    private RegistrationStatus registrationStatus;
}
