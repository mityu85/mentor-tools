package mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.student.Student;
import mentortools.trainingclass.TrainingClass;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registrations")
public class Registration {

    public Registration(TrainingClass trainingClass, Student student, RegistrationStatus registrationStatus) {
        this.trainingClass = trainingClass;
        this.student = student;
        this.registrationStatus = registrationStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "trainingclass_id")
    private TrainingClass trainingClass;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "registration_status")
    private RegistrationStatus registrationStatus;
}
