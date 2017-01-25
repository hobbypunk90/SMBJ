package de.thm.mni.mhpp11.jActor.actors.ui.messages;

import de.thm.mni.mhpp11.jActor.actors.ui.interfaces.ActorController;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * Created by hobbypunk on 18.01.17.
 */
@Getter
public class StartUIMessage extends Message {
  private final Class<? extends ActorController> controllerClass;
  private final List<?> params;
  
  public StartUIMessage(Class<? extends ActorController> controllerClass) {
    this(controllerClass, Collections.EMPTY_LIST);
    
  }
  
  public StartUIMessage(Class<? extends ActorController> controllerClass, List<?> params) {
    super(Constants.GLOBALGROUP, Constants.UICREATOR);
    this.controllerClass = controllerClass;
    this.params = params;
  }
}
