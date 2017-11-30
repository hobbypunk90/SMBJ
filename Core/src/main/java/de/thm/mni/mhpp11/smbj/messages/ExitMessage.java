package de.thm.mni.mhpp11.smbj.messages;

import de.thm.mni.mhpp11.smbj.messages.base.UUIDMessage;

import java.util.List;
import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */
public class ExitMessage extends UUIDMessage {
  public ExitMessage(UUID source) {
    super(source);
  }
  public ExitMessage(UUID source, UUID sink) {
    super(source, sink);
  }
  public ExitMessage(UUID source, List<UUID> sinks) {
    super(source, sinks);
  }
}
