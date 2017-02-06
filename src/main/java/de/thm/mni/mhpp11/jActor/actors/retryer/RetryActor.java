package de.thm.mni.mhpp11.jActor.actors.retryer;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.ExitMessage;
import de.thm.mni.mhpp11.jActor.actors.interfaces.AbstractActor;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.RegisterMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.RegisteredMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.StartMessage;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;

import java.util.UUID;

/**
 * Created by hobbypunk on 20.01.17.
 */

public class RetryActor extends AbstractActor {
  
  private static Integer maxTries = 10;
  private static Logger log = (Logger)org.slf4j.LoggerFactory.getLogger(RetryActor.class);
  static {
    log.setLevel(Level.ALL);
  }
  
  public RetryActor(UUID group) {
    super(Constants.GLOBALGROUP);
  }
  
  
  @Override
  public Integer getID() {
    return Constants.RETRYER;
  }
  
  @Override
  public UUID getGroup() {
    return Constants.GLOBALGROUP;
  }
  
  @Override
  public void execute(Message msg) {
    if(msg instanceof ExitMessage) return;
    
    if(msg instanceof StartMessage) {
      send(new RegisterMessage(this.getGroup(), this));
    } else if(msg instanceof RegisteredMessage) {
      log.trace("Retryer started");
    } else {
      if (msg.nextTry() <= maxTries) {
        try {
          Thread.sleep(150);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        log.debug(msg.toString());
        this.send(msg);
      }
      //TODO else
    }
  }
}
