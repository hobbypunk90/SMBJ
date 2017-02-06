package de.thm.mni.mhpp11.jActor.actors.logging.messages;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.event.Level;

import java.util.UUID;

/**
 * Created by hobbypunk on 20.01.17.
 */
@Getter
public class InfoMessage extends LogMessage {
  
  public InfoMessage(@NonNull Class<?> cause, @NonNull String message) {
    super(Level.INFO, cause, message);
  }
  
  public InfoMessage(@NonNull Class<?> cause, @NonNull String title, @NonNull String message) {
    super(Level.INFO, cause, title, message);
  }
  
  public InfoMessage(@NonNull Class<?> cause, UUID group, @NonNull String message) {
    super(Level.INFO, cause, group, message);
  }
  
  public InfoMessage(@NonNull Class<?> cause, UUID group, @NonNull String title, @NonNull String message) {
    super(Level.INFO, cause, group, title, message);
  }
}
