package de.thm.mni.mhpp11.smbj.ui.controllers;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import javafx.stage.Stage;

import java.util.UUID;

/**
 * Created by hobbypunk on 18.01.17.
 */
public interface IActorController {
  Stage getStage();
  UUID getID();
  IActor getActor();
  
  void onClose();
}
