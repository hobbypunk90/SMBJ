package de.thm.mni.mhpp11.jActor.actors.logging.messages;

import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.event.Level;

import java.util.UUID;

/**
 * Created by hobbypunk on 20.01.17.
 */
@Getter
public class TraceMessage extends LogMessage {
  public TraceMessage(@NonNull Class<?> cause, @NonNull Message msg) {
    super(Level.TRACE, cause, msg.toString());
  }

  public TraceMessage(@NonNull Class<?> cause, @NonNull String message) {
    super(Level.TRACE, cause, message);
  }
  
  public TraceMessage(@NonNull Class<?> cause, @NonNull String title, @NonNull String message) {
    super(Level.TRACE, cause, title, message);
  }
  
  public TraceMessage(@NonNull Class<?> cause, UUID group, @NonNull String message) {
    super(Level.TRACE, cause, group, message);
  }
  
  public TraceMessage(@NonNull Class<?> cause, UUID group, @NonNull String title, @NonNull String message) {
    super(Level.TRACE, cause, group, title, message);
  }
}
