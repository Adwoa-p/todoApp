package todoapp.project.models.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDto<T> {
    private String message;
    private T response;
}
