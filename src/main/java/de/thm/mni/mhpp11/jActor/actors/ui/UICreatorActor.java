package de.thm.mni.mhpp11.jActor.actors.ui;

import de.thm.mni.mhpp11.jActor.actors.interfaces.IActor;
import de.thm.mni.mhpp11.jActor.actors.logging.messages.ErrorMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.MessageBus;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.RegisterMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.StartMessage;
import de.thm.mni.mhpp11.jActor.actors.ui.interfaces.ActorController;
import de.thm.mni.mhpp11.jActor.actors.ui.messages.StartUIMessage;
import de.thm.mni.mhpp11.jActor.actors.ui.splash.SplashController;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import javafx.application.Platform;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hobbypunk on 12.01.17.
 */
public class UICreatorActor implements IActor {
  private BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
  
  public UICreatorActor(UUID group) {}
  
  @Override
  public Integer getID() {
    return Constants.UICREATOR;
  }
  
  @Override
  public UUID getGroup() {return Constants.GLOBALGROUP;}
  
  
  @Override
  public MessageBus getBus() {
    return MessageBus.getInstance();
  }
  
  @Override
  public BlockingQueue<Message> getInbox() {
    return this.inbox;
  }
  
  @Override
  public UICreatorActor onStart() {
    new Thread(null, this, this.getClass().getSimpleName() + "-" + Thread.activeCount()).start();
    return this;
  }
  
  @Override
  public void execute(Message msg) {
    if(msg instanceof StartMessage) {
      send(new RegisterMessage(msg.getGroup(), this));
      new Thread(() -> SplashController.launch(SplashController.class)).start();
      
    } else if(msg instanceof StartUIMessage) {
      try {
        Platform.runLater(() -> {
          try {
            Class<? extends ActorController> c = ((StartUIMessage) msg).getControllerClass();
            List<?> params = ((StartUIMessage) msg).getParams();
            try {
              c.getMethod("configure", List.class).invoke(null, params);
            } catch (Exception e) {
              getBus().send(new ErrorMessage(this.getClass(), Constants.GLOBALGROUP, e));
              c.getMethod("configure").invoke(null);
            }
  
          } catch (Exception e) {
            exceptionExit(e);
          }
        });
      } catch (IllegalStateException e) {
        getBus().send(msg);
      }
    }
  }
}
