package jr.examples.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreateBookRequest", description = "Information to create a book.")
public class CreateBookRequest {
    @Schema(description = "Title of the book", required = true)
    private String title;
    @Schema(description = "Genre of the book", required = true)
    private String genre;
    @Schema(description = "Price of the book", required = true)
    private double price;
    @Schema(description = "Reference to the author of the book", required = true)
    private Long authorId;
}
