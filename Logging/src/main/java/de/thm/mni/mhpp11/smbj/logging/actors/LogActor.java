package de.thm.mni.mhpp11.smbj.logging.actors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.thm.mni.mhpp11.smbj.actors.AbstractActor;
import de.thm.mni.mhpp11.smbj.logging.messages.*;
import de.thm.mni.mhpp11.smbj.logging.messages.LogMessage;
import de.thm.mni.mhpp11.smbj.messages.base.Message;
import de.thm.mni.mhpp11.smbj.manager.messages.StartedMessage;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by hobbypunk on 12.01.17.
 */

@Getter
public class LogActor extends AbstractActor {
  public static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
  private static final Map<Class, Logger> loggerMap = new HashMap<>();
  static {
    Logger logger = (Logger)org.slf4j.LoggerFactory.getLogger(LogActor.class);
    logger.setLevel(Level.ALL);
    loggerMap.put(LogActor.class, logger);
  }
  
  public LogActor(UUID id) {
    super(id);
  }
  
  @Override
  public UUID getID() {
    return LogActor.ID;
  }
  
  public void execute(Message msg) {
    if(msg instanceof StartedMessage) {
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
