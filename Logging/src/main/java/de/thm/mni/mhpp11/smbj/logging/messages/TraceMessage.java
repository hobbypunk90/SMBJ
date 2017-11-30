package de.thm.mni.mhpp11.smbj.logging.messages;

import de.thm.mni.mhpp11.smbj.messages.base.Message;
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
    super(Level.TRACE, cause, msg.getSource(), msg.toString());
  }

  public TraceMessage(@NonNull Class<?> cause, @NonNull String message) {
    super(Level.TRACE, cause, message);
  }
  
  public TraceMessage(@NonNull Class<?> cause, @NonNull String title, @NonNull String message) {
    super(Level.TRACE, cause, title, message);
  }
  
  public TraceMessage(@NonNull Class<?> cause, UUID source, @NonNull String message) {
    super(Level.TRACE, cause, source, message);
  }
  
  public TraceMessage(@NonNull Class<?> cause, UUID source, @NonNull String title, @NonNull String message) {
    super(Level.TRACE, cause, source, title, message);
  }
}
