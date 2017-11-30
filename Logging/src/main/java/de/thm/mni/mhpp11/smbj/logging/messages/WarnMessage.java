package de.thm.mni.mhpp11.smbj.logging.messages;

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
  
  public WarnMessage(@NonNull Class<?> cause, UUID source, @NonNull String message) {
    super(Level.WARN, cause, source, message);
  }
  
  public WarnMessage(@NonNull Class<?> cause, UUID source, @NonNull String title, @NonNull String message) {
    super(Level.WARN, cause, source, title, message);
  }
}
