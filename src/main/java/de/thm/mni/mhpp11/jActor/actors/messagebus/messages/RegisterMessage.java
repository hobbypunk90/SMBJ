package de.thm.mni.mhpp11.jActor.actors.messagebus.messages;

import de.thm.mni.mhpp11.jActor.actors.interfaces.IActor;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import lombok.Getter;

import java.util.UUID;

/**
 * Created by hobbypunk on 16.01.17.
 */
@Getter
public class RegisterMessage extends Message {
  private final IActor actor;
  
  public RegisterMessage(UUID group, IActor actor) {
    super(group, actor.getID());
    this.actor = actor;
  }
}
