package de.thm.mni.mhpp11.smbj.manager;

import de.thm.mni.mhpp11.smbj.actors.AbstractActor;
import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.logging.messages.ErrorMessage;
import de.thm.mni.mhpp11.smbj.manager.messages.InitMessage;
import de.thm.mni.mhpp11.smbj.manager.messages.InitStartMessage;
import de.thm.mni.mhpp11.smbj.manager.messages.StartMessage;
import de.thm.mni.mhpp11.smbj.manager.messages.StartedMessage;
import de.thm.mni.mhpp11.smbj.messages.ErrorExitMessage;
import de.thm.mni.mhpp11.smbj.messages.ExitMessage;
import de.thm.mni.mhpp11.smbj.messages.base.Message;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.val;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Created by hobbypunk on 18.01.17.
 */

@Value
@EqualsAndHashCode(callSuper = true)
public class ActorManager extends AbstractActor {
  
  @NonFinal
  private static ActorManager instance;
  
  public static ActorManager initialize(ActorManagerPlugin... plugins) {
    if(instance != null) throw new RuntimeException("ActorManager already initialized!");
    instance = new ActorManager(plugins);
    instance.executor.execute(instance);
    return getInstance();
  }
  
  public static ActorManager getInstance() {
    if(instance == null) throw new RuntimeException("ActorManager not initialized!");
    return instance;
  }
  
  HashMap<Class<? extends IActor>, List<IActor>> classGroupedActors = new HashMap<>();
  HashMap<UUID, IActor> actors = new HashMap<>();
  ExecutorService executor = Executors.newCachedThreadPool();
  
  List<ActorManagerPlugin> plugins = new ArrayList<>();
  MessageHandler handler = new MessageHandler();
  
  private ActorManager() {
    super(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    this.plugins.add(new BaseActorManagerPlugin());
  }
  
  private ActorManager(ActorManagerPlugin... plugins) {
    this();
    this.plugins.addAll(0, Arrays.asList(plugins));
  }
  
  @Override
  public void send(Message msg) {
    this.receive(msg);
  }
  
  @Override
  public void execute(Message msg) {
    if(msg instanceof ErrorExitMessage || (msg instanceof ExitMessage && ((ExitMessage) msg).getSinks().contains(getID()))) {
      handler.offerAll(msg);
    } else if (msg instanceof StartMessage) {
      try {
        ActorManagerPlugin plugin = null;
        for(val p : this.plugins) {
          if(p.handleStartMessage((StartMessage)msg)) {
            plugin = p;
            break;
          }
        }
        if(plugin != null) {
          IActor actor = plugin.processStartMessage((StartMessage) msg, UUID.randomUUID());
          addActor(actor);
          if(msg instanceof InitStartMessage)
            ((InitStartMessage) msg).init(actor.getID(), actor);
          actor.receive(new InitMessage(getID(), actor.getID()));
          executor.execute(actor);
          actor.receive(new StartedMessage(getID(), actor.getID()));
          ((StartMessage) msg).answer(actor.getID(), actor);
        }
      } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
        exceptionExit(e);
      }
    } else {
      try {
        for(val p : this.plugins) {
          if(p.handleMessage(msg)) {
            p.processMessage(handler, msg);
          }
        }
      } catch (NoSuchElementException e) {
        send(new ErrorMessage(this.getClass(), this.getID(), e));
      }
      
      if(msg instanceof ErrorMessage) {
        Boolean handleError = false;
        for(val p : this.plugins) {
          if(p.handleMessage(msg)) {
            handleError = true;
            break;
          }
        }
        if(!handleError) {
          ((ErrorMessage) msg).getThrowable().printStackTrace();
        }
      }
    }
  }
  
  public class MessageHandler {
    @SuppressWarnings("unchecked")
    void offerAll(Message msg) {
      val tmpActors = (HashMap<UUID, IActor>) actors.clone();
      tmpActors.forEach((uuid, iActor) -> offerActor(iActor, msg));
    }
  
    public void offerClasses(List<Class<? extends IActor>> clazzList, Message msg) {
      if(clazzList.isEmpty()) offerAll(msg);
      else clazzList.forEach(aClass -> offerClass(aClass, msg));
    }
  
    @SuppressWarnings("unchecked")
    public void offerClass(Class<? extends IActor> aClass, Message msg) {
      if(!classGroupedActors.containsKey(aClass))
        throw new NoSuchElementException("No such class in grouped list: " + aClass.getName());
      val tmpClassGroupedActors = (HashMap<Class<? extends IActor>, List<IActor>>) classGroupedActors.clone();
      tmpClassGroupedActors.get(aClass).forEach(actor -> offerActor(actor, msg));
    }
  
    public void offerUUIDs(List<UUID> uuidList, Message msg) {
      if(uuidList.isEmpty()) offerAll(msg);
      else uuidList.forEach(uuid -> offerUUID(uuid, msg));
    }
  
    public void offerUUID(UUID uuid, Message msg) {
      if(!actors.containsKey(uuid))
        throw new NoSuchElementException("No such uuid in actors");
    
      offerActor(actors.get(uuid), msg);
    }
  
    void offerActor(IActor actor, Message msg) {
      actor.receive(msg);
      if(msg instanceof ExitMessage || msg instanceof ErrorExitMessage) {
        removeActor(actor.getID());
      }
    }
  }
  
  private void addActor(IActor actor) {
    actors.put(actor.getID(), actor);
    if(!classGroupedActors.containsKey(actor.getClass()))
      classGroupedActors.put(actor.getClass(), new ArrayList<>());
    classGroupedActors.get(actor.getClass()).add(actor);
  }
  
  private void removeActor(UUID uuid) {
    if(!actors.containsKey(uuid))
      throw new NoSuchElementException("No such uuid in actors");
    IActor actor = actors.get(uuid);
    if(!classGroupedActors.containsKey(actor.getClass()))
      throw new NoSuchElementException("No such class in grouped list");
    if(!classGroupedActors.get(actor.getClass()).contains(actor))
      throw new NoSuchElementException("No such actor in grouped list");
    
    actors.remove(uuid);
    classGroupedActors.get(actor.getClass()).remove(actor);
    if(classGroupedActors.get(actor.getClass()).isEmpty())
      classGroupedActors.remove(actor.getClass());
    
    
    if(actorsSize() <= 0)
      receive(new ExitMessage(getID()));
  }
  
  @Override
  public void onStop(Message msg) {
    executor.shutdown();
    if(msg instanceof ErrorExitMessage) {
      try {
        executor.awaitTermination(10, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.exit(1);
    }
  }
  
  private long actorsSize() {
    Predicate<IActor> predicate = null;
    for(ActorManagerPlugin plugin : plugins)
      if(predicate == null) predicate = plugin;
      else predicate = predicate.or(plugin);
  
    if (predicate != null) {
      return this.actors.values().stream().filter(predicate.negate()).count();
    }
  
    return 0;
  }
}
