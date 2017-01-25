package de.thm.mni.mhpp11.jActor.actors.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.thm.mni.mhpp11.jActor.actors.interfaces.AbstractActor;
import de.thm.mni.mhpp11.jActor.actors.logging.messages.*;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.RegisterMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.RegisteredMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.StartMessage;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */

public class LogActor extends AbstractActor {
  private static final Map<Class, Logger> loggerMap = new HashMap<>();
  static {
    Logger logger = (Logger)org.slf4j.LoggerFactory.getLogger(LogActor.class);
    logger.setLevel(Level.ALL);
    loggerMap.put(LogActor.class, logger);
  }
  
  public LogActor(UUID group) {
    super(Constants.GLOBALGROUP);
  }
  
  @Override
  public Integer getID() {
    return Constants.LOGGER;
  }
  
  @Override
  public UUID getGroup() {return Constants.GLOBALGROUP;}
  
  @Override
  public void execute(Message msg) {
    if(msg instanceof StartMessage) {
      send(new RegisterMessage(this.getGroup(), this));
    } else if(msg instanceof RegisteredMessage) {
      log(loggerMap.get(LogActor.class), new TraceMessage(LogActor.class, "Logging started"));
    } else if(msg instanceof LogMessage) {
      LogMessage logMsg = (LogMessage) msg;
      if(!loggerMap.containsKey(logMsg.getCause())) {
        Logger logger = (Logger)org.slf4j.LoggerFactory.getLogger(logMsg.getCause());
        logger.setLevel(Level.ALL);
        loggerMap.put(logMsg.getCause(), logger);
      }
      
      log(loggerMap.get(logMsg.getCause()), logMsg);
    }
  }
  
  private void log(Logger log, LogMessage msg) {
    String message = msg.toString();
    
    if(msg instanceof TraceMessage) {
      log.trace(message);
    } else if(msg instanceof DebugMessage) {
      log.debug(message);
    } else if(msg instanceof InfoMessage) {
      log.info(message);
    } else if(msg instanceof WarnMessage) {
      log.warn(message);
    } else if(msg instanceof ErrorMessage) {
      log.error(message, ((ErrorMessage) msg).getThrowable());
    }
  }
}
