package mentortools;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mentor-tools")
public class TrainingClassController {

    private TrainingClassService trainingClassService;

    public TrainingClassController(TrainingClassService trainingClassService) {
        this.trainingClassService = trainingClassService;
    }

    @GetMapping
    public List<TrainingClassDto> listTrainingClasses(Optional<String> prefix) {
        return trainingClassService.listTrainingClasses(prefix);
    }

    @GetMapping("/{id}")
    public TrainingClassDto findTrainingClassById(@PathVariable("id") long id) {
        return trainingClassService.findTrainingClassById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingClassDto createTrainingClass(@Valid @RequestBody CreateTrainingClassCommand command) {
        return trainingClassService.createTrainingClass(command);
    }

    @PutMapping("/{id}")
    public TrainingClassDto updateTrainingClass(@PathVariable("id") long id, @Valid @RequestBody UpdateTrainingClassCommand command) {
        return trainingClassService.updateTrainingClass(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainingClass(@PathVariable("id") long id) {
        trainingClassService.deleteTrainingClass(id);
    }
}
