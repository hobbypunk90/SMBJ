package de.thm.mni.mhpp11.jActor.actors.messagebus;

import de.thm.mni.mhpp11.jActor.actors.interfaces.AbstractActor;
import de.thm.mni.mhpp11.jActor.actors.interfaces.IActor;
import de.thm.mni.mhpp11.jActor.actors.logging.LogActor;
import de.thm.mni.mhpp11.jActor.actors.logging.messages.LogMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.RegisterMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.RegisteredMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.StartMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.UnregisteredMessage;
import de.thm.mni.mhpp11.jActor.actors.retryer.RetryActor;
import de.thm.mni.mhpp11.jActor.actors.ui.UICreatorActor;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.ErrorExitMessage;
import de.thm.mni.mhpp11.jActor.messages.ExitMessage;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;

import java.util.*;

/**
 * Created by hobbypunk on 18.01.17.
 */
public class MessageBus extends AbstractActor {
 
  private static MessageBus INSTANCE = new MessageBus();
  public static MessageBus getInstance() {
    return INSTANCE;
  }
  
  private Map<UUID, Map<Integer, IActor>> actors = new HashMap<>();
  
  private MessageBus() {
    super(Constants.GLOBALGROUP);
    getInbox().offer(new StartMessage(LogActor.class, Constants.GLOBALGROUP));
    getInbox().offer(new StartMessage(RetryActor.class, Constants.GLOBALGROUP));
    getInbox().offer(new StartMessage(UICreatorActor.class, Constants.GLOBALGROUP));
  }
    
  @Override
  public Integer getID() {
    return Constants.BUS;
  }
  
  @Override
  public UUID getGroup() {return Constants.GLOBALGROUP;}
  
  @Override
  public void send(Message msg) {
    this.getInbox().offer(msg);
  }
  
  @Override
  public void execute(Message msg) {
    if(msg instanceof ErrorExitMessage) {
      offerAll(msg);
    } else if (msg instanceof StartMessage) {
      IActor actor = ((StartMessage)msg).newActor();
      actor.onStart().receive(msg);
    } else if(msg instanceof RegisterMessage) {
      if(!actors.containsKey(msg.getGroup())) actors.put(msg.getGroup(), new HashMap<>());
      if(actors.get(msg.getGroup()).containsKey(msg.getTo())) {
        actors.get(msg.getGroup()).get(msg.getTo()).receive(new UnregisteredMessage(msg.getGroup(), msg.getTo()));
      }
      actors.get(msg.getGroup()).put(msg.getTo(), ((RegisterMessage) msg).getActor());
      actors.get(msg.getGroup()).get(msg.getTo()).receive(new RegisteredMessage(msg.getGroup(), msg.getTo()));
      
    } else {
      try {
        if(msg instanceof LogMessage && msg.getGroup() != Constants.ALLGROUPS && msg.getGroup() != Constants.GLOBALGROUP) {
          offerGroupMember(Constants.GLOBALGROUP, Constants.LOGGER, msg);
        }
        
        if(msg.getGroup() == Constants.ALLGROUPS) {
          offerAllGroups(msg);
        } else if(actors.containsKey(msg.getGroup())){
          offerGroup(msg.getGroup(), msg);
        } else throw new NoSuchElementException(String.format("No such group %1$s", msg.getGroup()));
      } catch (NoSuchElementException e) {
        if(actors.containsKey(Constants.GLOBALGROUP) && actors.get(Constants.GLOBALGROUP).containsKey(Constants.RETRYER))
          actors.get(Constants.GLOBALGROUP).get(Constants.RETRYER).receive(msg);
        else
          this.getInbox().offer(msg);
      }
    }
  }
  
  private void offerAll(Message msg) {
    Set<UUID> groups = new HashSet<>(actors.keySet());
    groups.forEach(group -> {
      Set<Integer> groupActors = new HashSet<>(actors.get(group).keySet());
      groupActors.forEach(integer ->  {
        offerGroupMember(group, integer, msg);
      });
    });
  }
  
  private void offerAllGroups(Message msg) {
    Set<UUID> groups = new HashSet<>(actors.keySet());
    groups.forEach(group -> offerGroup(group, msg));
  }
  
  private void offerGroup(UUID group, Message msg) throws NoSuchElementException {
    if(actors.get(group).containsKey(msg.getTo()))
      offerGroupMember(group, msg.getTo(), msg);
    else {
      final Boolean[] sended = {false};
      
      Set<Integer> ids = new HashSet<>(actors.get(group).keySet());
      ids.forEach(id -> {
        if ((msg.getTo() & id) == id) {
          sended[0] = true;
          offerGroupMember(group, id, msg);
        }
      });
  
      if(!sended[0]) throw new NoSuchElementException(String.format("No such id %1$s", msg.getTo()));
    }
  }
  
  private void offerGroupMember(UUID group, Integer id, Message msg) {
    offerActor(actors.get(group).get(id), msg);
  }
  
  private void offerActor(IActor actor, Message msg) {
    actor.receive(msg);
    if(msg instanceof ExitMessage || msg instanceof ErrorExitMessage) {
      actors.get(actor.getGroup()).remove(actor.getID());
      if(actors.get(actor.getGroup()).size() == 0) {
        actors.remove(actor.getGroup());
        if(actors.size() == 1) {
          send(new ExitMessage(Constants.GLOBALGROUP, Constants.BUS | Constants.RETRYER | Constants.getId("ALL")));
        }
      }
    }
  }
  
  @Override
  public void onStop(Message msg) {
    if(msg instanceof ErrorExitMessage) {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.exit(1);
    }
  }
}
