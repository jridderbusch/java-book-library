package jr.examples.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Member", description = "Information about one member.")
public class MemberDto {
    @Schema(description = "The unique identifier of the member.", required = true)
    private Long id;
    @Schema(description = "The unique username of the member.", required = true)
    private String username;
    @Schema(description = "The full name of the member.", required = true)
    private String email;
    @Schema(description = "The address of the member.", required = true)
    private String address;
    @Schema(description = "The phone number of the member.", required = true)
    private String phoneNumber;
}
