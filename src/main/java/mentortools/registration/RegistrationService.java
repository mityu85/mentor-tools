package mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.student.Student;
import mentortools.student.StudentDto;
import mentortools.student.StudentService;
import mentortools.trainingclass.TrainingClass;
import mentortools.trainingclass.TrainingClassDto;
import mentortools.trainingclass.TrainingClassService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RegistrationService {

    private ModelMapper modelMapper;
    private RegistrationRepository repository;
    private StudentService studentService;
    private TrainingClassService trainingClassService;

    public List<StudentRegistrationDto> listRegisteredStudentsByTrainingClassId(Long id) {
        List<StudentRegistrationDto> studentRegistrations = new ArrayList<>();
        List<Registration> registrations = repository.findAll().stream()
                .filter(r -> r.getTrainingClass().getId() == id)
                .collect(Collectors.toList());
        for (Registration registration: registrations) {
            StudentRegistrationDto studentRegistrationDto = new StudentRegistrationDto(
                    registration.getStudent().getId(),
                    registration.getStudent().getName(),
                    registration.getRegistrationStatus()
            );
            studentRegistrations.add(studentRegistrationDto);
        }
        return studentRegistrations;
    }

    public RegistrationDto createStudentRegistrationByTrainingClass(Long trainingClassId, CreateRegistrationCommand command) {
        TrainingClass trainingClass = modelMapper.map(trainingClassService.findTrainingClassById(trainingClassId), TrainingClass.class);
        Student student = modelMapper.map(studentService.listStudentById(command.getStudentId()), Student.class);

        Registration registration = new Registration(
                trainingClass,
                student,
                RegistrationStatus.ACTIVE
        );
        repository.save(registration);
        return modelMapper.map(registration, RegistrationDto.class);
    }
}
