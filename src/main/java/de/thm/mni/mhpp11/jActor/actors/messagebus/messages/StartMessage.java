package de.thm.mni.mhpp11.jActor.actors.messagebus.messages;

import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.actors.interfaces.IActor;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */
@Getter
public class StartMessage extends Message {
  private final Class<? extends IActor> actorClass;
  
  public StartMessage(Class<? extends IActor> actorClass, UUID group) {
    super(group, Constants.NONE);
    this.actorClass = actorClass;
  }
  
  public IActor newActor() {
    try {
      return actorClass.getConstructor(UUID.class).newInstance(getGroup());
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      e.printStackTrace();
    }
  
    return null;
  }
  
  @Override
  public String toString() {
    return String.format("%s:", super.toString());
  }
}
