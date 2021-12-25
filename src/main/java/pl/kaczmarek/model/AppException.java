package pl.kaczmarek.model;

import org.springframework.http.HttpStatus;

public class AppException extends Exception {

  private static final long serialVersionUID = 1L;
  private HttpStatus status;

  public AppException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }

  public AppException(HttpStatus status, Throwable e){
    super(e);
    this.status = status;
  }

  public AppException(HttpStatus status, String message, Throwable e){
    super(message, e);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return this.status;
  }
}
