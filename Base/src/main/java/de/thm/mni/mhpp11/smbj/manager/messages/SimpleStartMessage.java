package de.thm.mni.mhpp11.smbj.manager.messages;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */
@Data
@Getter
@EqualsAndHashCode(callSuper = true)
public class SimpleStartMessage extends StartMessage {
  
  public SimpleStartMessage(Class<? extends IActor> actorClass) {
    super(actorClass);
  }
  
  @Override
  public Void answer(UUID id, IActor actor) {
    return null;
  }
}
