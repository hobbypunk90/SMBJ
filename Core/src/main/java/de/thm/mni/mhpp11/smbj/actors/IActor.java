package de.thm.mni.mhpp11.smbj.actors;

import de.thm.mni.mhpp11.smbj.logging.messages.ErrorMessage;
import de.thm.mni.mhpp11.smbj.logging.messages.LogMessage;
import de.thm.mni.mhpp11.smbj.manager.ActorManager;
import de.thm.mni.mhpp11.smbj.messages.ErrorExitMessage;
import de.thm.mni.mhpp11.smbj.messages.ExitMessage;
import de.thm.mni.mhpp11.smbj.messages.base.Message;
import org.slf4j.event.Level;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * Created by hobbypunk on 16.01.17.
 */
public interface IActor extends Runnable {
  
  UUID getID();
  
  default ActorManager getBus() throws IllegalAccessException {
    return ActorManager.getInstance();
  }
  
  BlockingQueue<Message> getInbox();
  
  default void send(Message msg) {
    try {
      if(this.getBus() != null)
        this.getBus().receive(msg);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
  default void receive(Message msg) {
    this.getInbox().offer(msg);
  }
  
  default void onStart() {
    this.send(new LogMessage(Level.TRACE, this.getClass(), "Started!"));
  }
  
  default void run() {
    onStart();
    Message msg = null;
    do {
      try {
        msg = this.getInbox().take();
  
        execute(msg);
      } catch (InterruptedException e) {
        System.err.println(this);
        e.printStackTrace();
      }
    } while(!exitCondition(msg));
    onStop(msg);
  }
  
  default void onStop(Message msg) {
    this.send(new LogMessage(Level.TRACE, this.getClass(), "Stopped!"));
  }
  
  default Boolean exitCondition(Message msg) {
    if(msg == null) return false;
    Boolean exit = msg instanceof ErrorExitMessage;
    exit |= msg instanceof ExitMessage && (((ExitMessage) msg).getSinks().isEmpty() || ((ExitMessage) msg).getSinks().contains(this.getID()));
    return exit;
  }
  
  void execute(Message msg);
  
  default void exceptionExit(Throwable t) {
    send(new ErrorMessage(this.getClass(), this.getID(), t));
    send(new ErrorExitMessage(this.getID()));
  }
}
