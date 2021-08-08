package mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.student.Student;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegistrationDto {

    private Long id;
    private String name;
    private RegistrationStatus registrationStatus;
}
