package mentortools.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentCommand {

    @NotBlank(message = "name cannot be empty")
    @Size(max = 255, message = "max length 255")
    private String name;

    @NotBlank(message = "email cannot be empty")
    @Size(max = 255, message = "max length 255")
    @Email
    private String email;

    @Size(max = 255, message = "max length 255")
    private String gitHubUserName;

    @Size(max = 255, message = "max length 255")
    private String desc;
}
