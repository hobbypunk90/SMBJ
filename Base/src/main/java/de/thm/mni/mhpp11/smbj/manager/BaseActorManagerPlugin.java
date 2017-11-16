package de.thm.mni.mhpp11.smbj.manager;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.messages.base.ClassMessage;
import de.thm.mni.mhpp11.smbj.messages.base.Message;
import de.thm.mni.mhpp11.smbj.messages.base.SinkedMessage;
import de.thm.mni.mhpp11.smbj.messages.base.UUIDMessage;
import de.thm.mni.mhpp11.smbj.manager.messages.StartMessage;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class BaseActorManagerPlugin extends ActorManagerPlugin{
  
  public Boolean handleStartMessage(StartMessage msg) {return true;}
  
  public IActor processStartMessage(StartMessage msg, UUID id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    return msg.getPayload().getConstructor(UUID.class).newInstance(id);
  }
  
  public Boolean handleMessage(Message msg) { return true; }
  
  public void processMessage(ActorManager.MessageHandler handler, Message msg) {
    if(msg instanceof SinkedMessage) {
      if(msg instanceof ClassMessage) {
        handler.offerClasses(((ClassMessage) msg).getSinks(), msg);
      }else if(msg instanceof UUIDMessage) {
        handler.offerUUIDs(((UUIDMessage) msg).getSinks(), msg);
      }
    }
  }
}
