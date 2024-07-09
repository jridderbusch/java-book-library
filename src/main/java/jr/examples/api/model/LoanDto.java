package jr.examples.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Loan", description = "Information about one loan.")
public class LoanDto {
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
}
