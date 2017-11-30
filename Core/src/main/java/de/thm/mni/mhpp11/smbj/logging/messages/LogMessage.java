package de.thm.mni.mhpp11.smbj.logging.messages;

import de.thm.mni.mhpp11.smbj.messages.base.tell.TellUUIDMessage;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.slf4j.event.Level;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LogMessage extends TellUUIDMessage<String> {
  Level level;
  Class<?> cause;
  String title;
  
  public LogMessage(@NonNull Level level, @NonNull Class<?> cause) {
    this(level, cause, "");
  }
  
  public LogMessage(@NonNull Level level, @NonNull Class<?> cause, @NonNull String message) {
    this(level, cause, "", message);
  }
  
  public LogMessage(@NonNull Level level, @NonNull Class<?> cause, @NonNull String title, @NonNull String message) {
    this(level, cause, null, title, message);
  }
  
  public LogMessage(@NonNull Level level, @NonNull Class<?> cause, UUID source) {
    this(level, cause, source, "");
  }
  
  public LogMessage(@NonNull Level level, @NonNull Class<?> cause, UUID source, @NonNull String message) {
    this(level, cause, source, "", message);
  }
  
  public LogMessage(@NonNull Level level, @NonNull Class<?> cause, UUID source, @NonNull String title, @NonNull String message) {
    this(level, cause, source, Collections.EMPTY_LIST, title, message);
  }
  
  private LogMessage(@NonNull Level level, @NonNull Class<?> cause, UUID source, @NonNull List<UUID> sinks, @NonNull String message) {
    this(level, cause, source, sinks, "", message);
  }
  
  private LogMessage(@NonNull Level level, @NonNull Class<?> cause, UUID source, @NonNull List<UUID> sinks, @NonNull String title, @NonNull String message) {
    super(source, sinks, message);
    this.level = level;
    this.cause = cause;
    this.title = title;
  }
  
  public String getMessage() {
    return this.getPayload();
  }
  
  public String getSimpleCause() {
    return this.cause.getSimpleName();
  }
  
  public String toString() {
    return (this.getTitle().isEmpty()) ? String.format("%s", this.getMessage()) : String.format("%s, %s", this.getTitle(), this.getMessage());
  }
}
