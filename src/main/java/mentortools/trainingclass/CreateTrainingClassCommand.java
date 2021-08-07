package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateTrainingClassCommand {

    @NotBlank(message = "name cannot be empty")
    @Size(max = 255, message = "length cannot be more than 255 characters")
    private String name;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    public CreateTrainingClassCommand(String name, LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("end date must be later than start date");
        }
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
