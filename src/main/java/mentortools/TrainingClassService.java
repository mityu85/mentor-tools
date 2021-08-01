package mentortools;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingClassService {

    private ModelMapper modelMapper;
    private TrainingClassRepository repository;

    public List<TrainingClassDto> listTrainingClasses(Optional<String> prefix) {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, TrainingClassDto.class))
                .collect(Collectors.toList());
    }

    public TrainingClassDto findTrainingClassById(long id) {
        return modelMapper.map(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id cannot found " + id)), TrainingClassDto.class);
    }

    public TrainingClassDto createTrainingClass(CreateTrainingClassCommand command) {
        return modelMapper.map(repository.save(new TrainingClass(
                command.getName(),
                command.getStartDate(),
                command.getEndDate()
        )), TrainingClassDto.class);
    }

    @Transactional
    public TrainingClassDto updateTrainingClass(long id, UpdateTrainingClassCommand command) {
        TrainingClass trainingClass = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id cannot found " + id));
        trainingClass.setName(command.getName());
        trainingClass.setStartDate(command.getStartDate());
        trainingClass.setEndDate(command.getEndDate());
        repository.save(trainingClass);
        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    public void deleteTrainingClass(long id) {
        repository.deleteById(id);
    }

    public void deleteAllTrainingClass() {
        repository.deleteAll();
    }
}
