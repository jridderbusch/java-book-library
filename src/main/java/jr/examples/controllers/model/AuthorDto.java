package jr.examples.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Author", description = "Information about one author.")
public class AuthorDto {
    @Schema(description = "The unique identifier of the author.", required = true)
    private Long id;
    @Schema(description = "The name of the author.", required = true)
    private String name;
    @Schema(description = "The date of birth of the author.", required = true)
    private OffsetDateTime dob;
    @Schema(description = "The list of references to books written by the author.", required = true)
    public List<Long> bookIds;
}
