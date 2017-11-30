package de.thm.mni.mhpp11.smbj.manager.messages;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.messages.base.ask.AskUUIDMessage;
import lombok.Getter;

import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */
@Getter
public abstract class StartMessage extends AskUUIDMessage<Class<? extends IActor>, IActor, Void> {
  
  public StartMessage(Class<? extends IActor> actorClass) {
    this(null, actorClass);
  }
  
  public StartMessage(UUID source, Class<? extends IActor> actorClass) {
    super(source, actorClass);
  }
  
  
  @Override
  public abstract Void answer(UUID id, IActor actor);
  
}
