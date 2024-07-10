package jr.examples.controllers.model.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreateLoanRequest", description = "Information to create a loan.")
public class CreateLoanRequest {
    @Schema(description = "Reference to the member who is borrowing the book.", required = true)
    private Long memberId;
    @Schema(description = "Reference to the book that is being borrowed.", required = true)
    private Long bookId;
    @Schema(description = "The date and time when the book was borrowed.", required = true)
    private LocalDate lendDate;
    @Schema(description = "The date when the book is due to be returned.", required = true)
    private LocalDate dueDate;
}
