package de.thm.mni.mhpp11.jActor.actors.logging.messages;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

/**
 * Created by hobbypunk on 20.01.17.
 */
@Getter
public class InfoMessage extends LogMessage {
  
  public InfoMessage(@NonNull Class<?> cause, @NonNull String message) {
    super(cause, message);
  }
  
  public InfoMessage(@NonNull Class<?> cause, @NonNull String title, @NonNull String message) {
    super(cause, title, message);
  }
  
  public InfoMessage(@NonNull Class<?> cause, UUID group, @NonNull String message) {
    super(cause, group, message);
  }
  
  public InfoMessage(@NonNull Class<?> cause, UUID group, @NonNull String title, @NonNull String message) {
    super(cause, group, title, message);
  }
}
