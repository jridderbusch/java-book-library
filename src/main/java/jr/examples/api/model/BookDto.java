package jr.examples.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Book", description = "Information about one book.")
public class BookDto {
    private String title;
    private String genre;
    private double price;
    private AuthorDto author;
}
