package jr.examples.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Author", description = "Information about one author.")
public class AuthorDto {
    private String name;
    private OffsetDateTime dateOfBirth;
}
