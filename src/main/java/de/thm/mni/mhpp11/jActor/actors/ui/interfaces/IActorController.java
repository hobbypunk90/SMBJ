package de.thm.mni.mhpp11.jActor.actors.ui.interfaces;

import de.thm.mni.mhpp11.jActor.actors.interfaces.IActor;
import javafx.stage.Stage;

import java.util.UUID;

/**
 * Created by hobbypunk on 18.01.17.
 */
public interface IActorController {
  Stage getStage();
  UUID getGroup();
  IActor getActor();
  
  void onClose();
}
