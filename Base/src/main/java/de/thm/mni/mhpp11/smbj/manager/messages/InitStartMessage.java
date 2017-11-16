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
public abstract class InitStartMessage extends StartMessage {
  
  public InitStartMessage(Class<? extends IActor> actorClass) {
    super(actorClass);
  }
  
  public InitStartMessage(UUID source, Class<? extends IActor> actorClass) {
    super(source, actorClass);
  }
  
  public abstract Void init(UUID id, IActor actor);
  
  @Override
  public abstract Void answer(UUID id, IActor actor);
  
}
