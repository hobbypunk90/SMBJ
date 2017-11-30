package de.thm.mni.mhpp11.smbj.logging.messages;

import lombok.Getter;
import lombok.NonNull;
import org.slf4j.event.Level;

import java.util.UUID;

/**
 * Created by hobbypunk on 20.01.17.
 */
@Getter
public class DebugMessage extends LogMessage {
  public DebugMessage(@NonNull Class<?> cause, @NonNull String message) {
    super(Level.DEBUG, cause, message);
  }
  public DebugMessage(@NonNull Class<?> cause, @NonNull String title, @NonNull String message) {
    super(Level.DEBUG, cause, title, message);
  }
  
  public DebugMessage(@NonNull Class<?> cause, @NonNull UUID source, @NonNull String message) {
    super(Level.DEBUG, cause, source, message);
  }
  public DebugMessage(@NonNull Class<?> cause, @NonNull UUID source, @NonNull String title, @NonNull String message) {
    super(Level.DEBUG, cause, source, title, message);
  }
}
