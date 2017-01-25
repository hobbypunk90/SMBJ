package de.thm.mni.mhpp11.jActor.actors.logging.messages;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

/**
 * Created by hobbypunk on 20.01.17.
 */
@Getter
public class DebugMessage extends LogMessage {
  public DebugMessage(@NonNull Class<?> cause, @NonNull String message) {
    super(cause, message);
  }
  public DebugMessage(@NonNull Class<?> cause, @NonNull String title, @NonNull String message) {
    super(cause, title, message);
  }
  
  public DebugMessage(@NonNull Class<?> cause, @NonNull UUID group, @NonNull String message) {
    super(cause, group, message);
  }
  public DebugMessage(@NonNull Class<?> cause, @NonNull UUID group, @NonNull String title, @NonNull String message) {
    super(cause, group, title, message);
  }
}
