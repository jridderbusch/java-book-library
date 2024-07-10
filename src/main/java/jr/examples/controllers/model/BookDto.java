package jr.examples.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Book", description = "Information about one book.")
public class BookDto {
    @Schema(description = "The unique identifier of the book.", required = true)
    private Long id;
    @Schema(description = "The title of the book.", required = true)
    private String title;
    @Schema(description = "The genre of the book.", required = true)
    private String genre;
    @Schema(description = "The price of the book.", required = true)
    private Double price;
    @Schema(description = "Reference to the author of the book.", required = true)
    private Long authorId;
}
