package uz.pdp.hrmanagement.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    private String errorMessage;
    private Long timestamp = System.currentTimeMillis();
    private T data;

    public ResponseData(T data) {
        this.data = data;
        this.errorMessage = "";
    }

    public ResponseData(String errorMessage, T data) {
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public ResponseData() {
        this.errorMessage = "";
    }

    public static <T> ResponseEntity<ResponseData<T>> response(T data) {
        return ResponseEntity.ok(new ResponseData<>(data));
    }

    public static <T> ResponseEntity<ResponseData<T>> response(ResponseData<T> responseData, HttpStatus httpStatus) {
        return new ResponseEntity<>(responseData, httpStatus);
    }

    public static <T> ResponseEntity<ResponseData<T>> response(String errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseData<>(errorMessage, null), httpStatus);
    }
}
