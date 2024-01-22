package object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Response {
    private String maLoi ;
    private Object noiDung ;
    private String trangThai ;
}
