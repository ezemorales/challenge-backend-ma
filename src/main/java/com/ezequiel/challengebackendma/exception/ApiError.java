package com.ezequiel.challengebackendma.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

  private HttpStatus status;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  private List<String> errors;


  private ApiError() {
    timestamp = LocalDateTime.now();
  }

  public ApiError(HttpStatus status, List<String> errors) {
    super();
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.errors = errors;
  }

  public ApiError(HttpStatus status) {
    super();
    this.timestamp = LocalDateTime.now();
    this.status = status;
  }

  public ApiError(HttpStatus status, String error) {
    super();
    this.timestamp = LocalDateTime.now();
    this.status = status;
    errors = Arrays.asList(error);
  }

}
