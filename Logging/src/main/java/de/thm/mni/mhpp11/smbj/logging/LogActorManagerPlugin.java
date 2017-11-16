package de.thm.mni.mhpp11.smbj.logging;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.logging.actors.LogActor;
import de.thm.mni.mhpp11.smbj.logging.messages.LogMessage;
import de.thm.mni.mhpp11.smbj.manager.ActorManager;
import de.thm.mni.mhpp11.smbj.manager.ActorManagerPlugin;
import de.thm.mni.mhpp11.smbj.messages.base.Message;

public class LogActorManagerPlugin extends ActorManagerPlugin {
  
  public Boolean handleMessage(Message msg) { return msg instanceof LogMessage && !((LogMessage) msg).getSinks().isEmpty() && !((LogMessage) msg).getSinks().contains(LogActor.ID); }
  
  public void processMessage(ActorManager.MessageHandler handler, Message msg) {
      handler.offerUUID(LogActor.ID, msg);
  }
  
  @Override
  protected Class<? extends IActor> isActorClass() {
    return LogActor.class;
  }
}
