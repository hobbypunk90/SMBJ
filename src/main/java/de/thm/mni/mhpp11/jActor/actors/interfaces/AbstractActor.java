package de.thm.mni.mhpp11.jActor.actors.interfaces;

import de.thm.mni.mhpp11.jActor.actors.messagebus.MessageBus;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hobbypunk on 12.01.17.
 */
@Getter
public abstract class AbstractActor implements IActor {
  private final UUID group;
  
  public AbstractActor(UUID group) {
    this.group = group;
  }
  
  private BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
  
  public BlockingQueue<Message> getInbox() {
    return this.inbox;
  }
  
  public MessageBus getBus() {
    return MessageBus.getInstance();
  }
  
  public IActor onStart() {
    new Thread(null,this, this.getClass().getSimpleName() + "-" + Thread.activeCount()).start();
    
    return this;
  }
}
