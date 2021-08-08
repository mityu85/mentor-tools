package mentortools;

import mentortools.registration.*;
import mentortools.student.StudentDto;
import mentortools.trainingclass.TrainingClassDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/trainingclasses/{id}/registrations")
    public List<StudentRegistrationDto> listRegisteredStudentsByTrainingClassId(@PathVariable("id") Long id) {
        return registrationService.listRegisteredStudentsByTrainingClassId(id);
    }

    @PostMapping("/trainingclasses/{trainingClassId}/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationDto createStudentRegistration(@PathVariable("trainingClassId") Long trainingClassId, @RequestBody CreateRegistrationCommand command) {
        return registrationService.createStudentRegistrationByTrainingClass(trainingClassId, command);
    }
}
