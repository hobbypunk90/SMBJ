package de.thm.mni.mhpp11.jActor.actors.interfaces;

import de.thm.mni.mhpp11.jActor.actors.logging.messages.ErrorMessage;
import de.thm.mni.mhpp11.jActor.actors.logging.messages.TraceMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.MessageBus;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.ErrorExitMessage;
import de.thm.mni.mhpp11.jActor.messages.ExitMessage;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * Created by hobbypunk on 16.01.17.
 */
public interface IActor extends Runnable {
  
  Integer getID();
  UUID getGroup();
  
  default MessageBus getBus() {
    return MessageBus.getInstance();
  }
  
  BlockingQueue<Message> getInbox();
  
  default void send(Message msg) {
    if(this.getBus() != null)
      this.getBus().send(msg);
  }
  default void receive(Message msg) {
    this.getInbox().offer(msg);
  }
  
  default void run() {
    this.send(new TraceMessage(this.getClass(), this.getGroup(), "Started!"));
    Message lastMsg = null;
    
    while(!exitCondition(lastMsg)) {
      try {
        lastMsg = this.getInbox().take();
        execute(lastMsg);
      } catch (InterruptedException e) {
        System.err.println(this);
        e.printStackTrace();
      }
    }
  
    this.send(new TraceMessage(this.getClass(), Constants.GLOBALGROUP, "Stopped!"));
    onStop(lastMsg);
  }
  
  IActor onStart();
  
  default void onStop(Message lastMsg) {}
  
  default Boolean exitCondition(Message lastMsg) {
    if(lastMsg == null) return false;
    Boolean exit = lastMsg instanceof ErrorExitMessage;
    exit |= lastMsg instanceof ExitMessage && (lastMsg.getGroup().equals(this.getGroup()) || lastMsg.getGroup().equals(Constants.ALLGROUPS)) && this.getID() == (lastMsg.getTo() & this.getID());
    return exit;
  }
  
  void execute(Message msg);
  
  default void exceptionExit(Throwable t) {
    send(new ErrorMessage(this.getClass(), t));
    send(new ErrorExitMessage(Constants.GLOBALGROUP, Constants.BUS | Constants.RETRYER | Constants.getId("ALL")));
  }
}
