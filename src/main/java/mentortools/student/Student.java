package mentortools.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Column(name = "github_username")
    private String gitHubUserName;

    @Column(name = "description")
    private String desc;

    public Student(String name, String email, String gitHubUserName, String desc) {
        this.name = name;
        this.email = email;
        this.gitHubUserName = gitHubUserName;
        this.desc = desc;
    }
}
