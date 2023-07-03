package at.htlstp.aslan.houserent.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CustomResponseObject {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    public CustomResponseObject(String message) {
        this.message = message;
    }
}
