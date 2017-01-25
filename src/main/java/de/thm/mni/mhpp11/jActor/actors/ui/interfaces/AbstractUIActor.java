package de.thm.mni.mhpp11.jActor.actors.ui.interfaces;

import de.thm.mni.mhpp11.jActor.actors.interfaces.IActor;
import de.thm.mni.mhpp11.jActor.actors.messagebus.MessageBus;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.UnregisteredMessage;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.ExitMessage;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hobbypunk on 18.01.17.
 */
public abstract class AbstractUIActor<C extends IActorController, T extends Message> extends Task<T> implements IActor {
  
  private BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
  
  @Getter(AccessLevel.PROTECTED) private final Stage stage;
  @Getter(AccessLevel.PROTECTED) private final C controller;
  
  protected AbstractUIActor(C controller) {
    this.controller = controller;
    this.stage = controller.getStage();
  }
  
  @Override
  public UUID getGroup() {
    return getController().getGroup();
  }
  
  @Override
  public Integer getID() {
    return Constants.UI;
  }
  
  @Override
  public MessageBus getBus() {
    return MessageBus.getInstance();
  }
  
  @Override
  public BlockingQueue<Message> getInbox() {
    return this.inbox;
  }
  
  @Override
  public IActor onStart() {
    new Thread(null,this, this.getClass().getSimpleName() + "-" + Thread.activeCount()).start();
    return this;
  }
  
  @Override
  public final void execute(Message msg) {
    if(msg instanceof ExitMessage) {
      Platform.runLater(this.stage::hide);
    }
    
    Platform.runLater(() -> executeUI((T)msg));
  }
  
  public abstract void executeUI(T msg);
  
  @Override
  protected T call() throws Exception {
    IActor.super.run();
    return null;
  }
  
  @Override
  public void onStop(Message lastMsg) {
    Platform.runLater(() ->getController().onClose());
  }
  
  @Override
  public Boolean exitCondition(Message lastMsg) {
    return IActor.super.exitCondition(lastMsg) || lastMsg instanceof UnregisteredMessage;
  }
}
