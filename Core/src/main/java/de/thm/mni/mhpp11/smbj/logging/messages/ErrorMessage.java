package de.thm.mni.mhpp11.smbj.logging.messages;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.event.Level;

import java.util.UUID;

/**
 * Created by hobbypunk on 20.01.17.
 */
@Getter
public class ErrorMessage extends LogMessage {
  private final Throwable throwable;
  
  public ErrorMessage(@NonNull Class<?> cause, @NonNull Throwable throwable) {
    super(Level.ERROR, cause);
    this.throwable = throwable;
  }
  
  public ErrorMessage(@NonNull Class<?> cause, @NonNull String title, @NonNull Throwable throwable) {
    super(Level.ERROR, cause, title);
    this.throwable = throwable;
  }
  
  public ErrorMessage(@NonNull Class<?> cause, @NonNull UUID source, @NonNull Throwable throwable) {
    super(Level.ERROR, cause, source);
    this.throwable = throwable;
  }

  public ErrorMessage(@NonNull Class<?> cause, @NonNull UUID source, @NonNull String title, @NonNull Throwable throwable) {
    super(Level.ERROR, cause, source, title);
    this.throwable = throwable;
  }
  
  @Override
  public String getMessage() {
    return throwable.getMessage();
  }
  
  public StackTraceElement[] getTrace() {
    return throwable.getStackTrace();
  }
}
