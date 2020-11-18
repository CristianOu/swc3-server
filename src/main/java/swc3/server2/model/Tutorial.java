package swc3.server2.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "tutorials", schema = "swc3_db")
public class Tutorial {
    @Id@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "title", nullable = false, length = 255)
    private String title;
    @Basic@Column(name = "description", nullable = true, length = 255)
    private String description;
    @Basic@Column(name = "published", nullable = true)
    private boolean published;

    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

}
