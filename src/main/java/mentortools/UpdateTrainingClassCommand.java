package mentortools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UpdateTrainingClassCommand {

    @NotNull(message = "name cannot be empty")
    @Size(max = 255, message = "length cannot be more than 255 characters")
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    public UpdateTrainingClassCommand(String name, LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("end date must be later than start date");
        }
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
