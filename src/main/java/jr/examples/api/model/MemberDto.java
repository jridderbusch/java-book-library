package jr.examples.api.model;

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
    private MemberDto member;
    private BookDto book;
    private OffsetDateTime lendDate;
    private OffsetDateTime dueDate;
}
