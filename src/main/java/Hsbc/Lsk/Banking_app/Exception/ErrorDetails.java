package Hsbc.Lsk.Banking_app.Exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp,
                           String message,
                           String details,
                           String errorCode) {


}
