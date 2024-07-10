package jr.examples.controllers.model.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreateAuthorRequest", description = "Information to create an author.")
public class CreateAuthorRequest {
    @Schema(description = "The name of the author.", required = true)
    private String name;
    @Schema(description = "The date of birth of the author.", required = true)
    private OffsetDateTime dob;
}
