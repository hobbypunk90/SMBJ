package de.thm.mni.mhpp11.jActor.actors.messagebus.messages;

import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import lombok.Getter;

import java.util.UUID;

/**
 * Created by hobbypunk on 16.01.17.
 */
@Getter
public class RegisteredMessage extends Message {
  
  public RegisteredMessage(UUID group, Integer id) {
    super(group, id);
  }
}
