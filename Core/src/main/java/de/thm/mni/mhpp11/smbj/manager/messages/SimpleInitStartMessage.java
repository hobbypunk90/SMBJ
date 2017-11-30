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
public abstract class SimpleInitStartMessage<T extends IActor> extends StartMessage {
  
  public SimpleInitStartMessage(Class<? extends IActor> actorClass) {
    super(actorClass);
  }
  
  public abstract Void init(UUID id, T actor);
  
  @Override
  public Void answer(UUID id, IActor actor) {
    return null;
  }
}
