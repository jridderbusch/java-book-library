package jr.examples.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Loan", description = "Information about one loan.")
public class LoanDto {
    @Schema(description = "The unique identifier of the loan.", required = true)
    private Long id;
    @Schema(description = "Reference to the member who is borrowing the book.", required = true)
    private Long memberId;
    @Schema(description = "Reference to the book that is being borrowed.", required = true)
    private Long bookId;
    @Schema(description = "The date when the book was borrowed.", required = true)
    private LocalDate lendDate;
    @Schema(description = "The date when the book is due to be returned.", required = true)
    private LocalDate dueDate;
}
