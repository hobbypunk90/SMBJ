package de.thm.mni.mhpp11.smbj.manager;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.messages.base.Message;
import de.thm.mni.mhpp11.smbj.manager.messages.StartMessage;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class ActorManagerPlugin implements Predicate<IActor> {
  
  public Boolean handleStartMessage(StartMessage msg) {return false;}
  
  public IActor processStartMessage(StartMessage msg, UUID id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {return null;}
  
  public Boolean handleMessage(Message msg) { return false; }
  
  public void processMessage(ActorManager.MessageHandler handler, Message msg) {}
  
  protected Class<? extends IActor> isActorClass() {
    return null;
  }
  
  @Override
  public boolean test(IActor actor) {
    return isActorClass() != null && isActorClass().isAssignableFrom(actor.getClass());
  }
}
