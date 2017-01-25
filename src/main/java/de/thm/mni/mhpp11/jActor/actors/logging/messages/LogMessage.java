package de.thm.mni.mhpp11.jActor.actors.logging.messages;

import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */
@Getter
public abstract class LogMessage extends Message {
  private final Class<?> cause;
  private final String title;
  private final String message;
  
  LogMessage(@NonNull Class<?> cause) {
    this(cause, "");
  }
  
  LogMessage(@NonNull Class<?> cause, @NonNull String message) {
    this(cause, Constants.GLOBALGROUP, Constants.LOGGER, "", message);
  }
  
  LogMessage(@NonNull Class<?> cause, @NonNull String title, @NonNull String message) {
    this(cause, Constants.GLOBALGROUP, Constants.LOGGER, title, message);
  }
  
  LogMessage(@NonNull Class<?> cause, UUID group) {
    this(cause, group, "");
  }
  
  LogMessage(@NonNull Class<?> cause, UUID group, @NonNull String message) {
    this(cause, group, Constants.UI | Constants.LOGGER, message);
  }
  
  LogMessage(@NonNull Class<?> cause, UUID group, @NonNull String title, @NonNull String message) {
    this(cause, group, Constants.UI | Constants.LOGGER, title, message);
  }

  private LogMessage(@NonNull Class<?> cause, UUID group, @NonNull Integer to, @NonNull String message) {
    this(cause, group, to, "", message);
  }
  
  private LogMessage(@NonNull Class<?> cause, UUID group, @NonNull Integer to, @NonNull String title, @NonNull String message) {
    super(group, to);
    this.cause = cause;
    this.title = title;
    this.message = message;
  }
  
  public String getSimpleCause() {
    return this.cause.getSimpleName();
  }
  
  @Override
  public String toString() {
    return String.format("%s: %s, try %2d", this.getGroup(), this.getMessage(), this.getTried());
  }
}
