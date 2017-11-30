package de.thm.mni.mhpp11.smbj.manager.messages;

import de.thm.mni.mhpp11.smbj.messages.base.UUIDMessage;
import lombok.Getter;

import java.util.UUID;

/**
 * Created by hobbypunk on 16.01.17.
 */
@Getter
public class StartedMessage extends UUIDMessage {
  
  public StartedMessage(UUID source, UUID sink) {
    super(source, sink);
  }
}
