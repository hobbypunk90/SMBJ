package de.thm.mni.mhpp11.jActor.actors.logging.messages;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.event.Level;

import java.util.UUID;

/**
 * Created by hobbypunk on 20.01.17.
 */
@Getter
public class WarnMessage extends LogMessage {
  public WarnMessage(@NonNull Class<?> cause, @NonNull String message) {
    super(Level.WARN, cause, message);
  }
  
  public WarnMessage(@NonNull Class<?> cause, @NonNull String title, @NonNull String message) {
    super(Level.WARN, cause, title, message);
  }
  
  public WarnMessage(@NonNull Class<?> cause, UUID group, @NonNull String message) {
    super(Level.WARN, cause, group, message);
  }
  
  public WarnMessage(@NonNull Class<?> cause, UUID group, @NonNull String title, @NonNull String message) {
    super(Level.WARN, cause, group, title, message);
  }
}
