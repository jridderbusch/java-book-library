package jr.examples.controllers.model.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UpdateMemberRequest", description = "Information to update a member.")
public class UpdateMemberRequest {
    @Schema(description = "The unique username of the member.", required = true)
    private String username;
    @Schema(description = "The email address of the member.", required = true)
    private String email;
    @Schema(description = "The address of the member.", required = true)
    private String address;
    @Schema(description = "The phone number of the member.", required = true)
    private String phoneNumber;
}
