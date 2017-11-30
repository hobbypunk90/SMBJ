package de.thm.mni.mhpp11.smbj.ui;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.manager.ActorManagerPlugin;
import de.thm.mni.mhpp11.smbj.manager.messages.StartMessage;
import de.thm.mni.mhpp11.smbj.ui.messages.StartUIMessage;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public class UIActorManagerPlugin extends ActorManagerPlugin{
  
  public Boolean handleStartMessage(StartMessage msg) {return msg instanceof StartUIMessage;}
  
  public IActor processStartMessage(StartMessage msg, UUID id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    return msg.getPayload().getConstructor(UUID.class, Stage.class, List.class).newInstance(id, ((StartUIMessage)msg).getStage(), ((StartUIMessage) msg).getParams());
  }
}
