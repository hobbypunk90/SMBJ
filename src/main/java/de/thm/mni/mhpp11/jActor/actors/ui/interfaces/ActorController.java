package de.thm.mni.mhpp11.jActor.actors.ui.interfaces;

import de.thm.mni.mhpp11.jActor.actors.interfaces.IActor;
import de.thm.mni.mhpp11.jActor.actors.messagebus.MessageBus;
import de.thm.mni.mhpp11.jActor.actors.messagebus.messages.RegisterMessage;
import de.thm.mni.mhpp11.jActor.actors.ui.splash.messages.SplashShowMessage;
import de.thm.mni.mhpp11.jActor.messages.ExitMessage;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.management.ReflectionException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by hobbypunk on 18.01.17.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class ActorController implements IActorController {
  
  private IActor actor;
  private UUID group;
  
  private Stage stage;
  private Scene scene;
  
  @Setter private List<?> params = Collections.EMPTY_LIST;
  
  protected void lateInitialize() throws ReflectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

    this.getStage().setOnCloseRequest(event -> {
      event.consume();
      MessageBus.getInstance().send(new ExitMessage(getGroup()));
    });
    
    Class<?>[] classes = this.getClass().getDeclaredClasses();
    Class<AbstractUIActor> actorClass = null;
    
    for(Class clazz : classes) {
      if(AbstractUIActor.class.isAssignableFrom(clazz)) {
        actorClass = clazz;
        break;
      }
    }
    
    if(actorClass == null) throw new ReflectionException(new ClassNotFoundException("No ActorClass in " + this.getClass()));
    Constructor<AbstractUIActor> actorConstructor = null;
    
    for(Constructor<?> constructor: actorClass.getDeclaredConstructors()) {
      if(constructor.getParameterCount() == 1 && IActorController.class.isAssignableFrom(constructor.getParameterTypes()[0])) {
        actorConstructor = (Constructor<AbstractUIActor>) constructor;
        break;
      }
    }
    if(actorConstructor == null) throw new ReflectionException(new ClassNotFoundException("No Matching Constructor in " + this.getClass()));
  
    actorConstructor.setAccessible(true);
    this.actor = actorConstructor.newInstance(this).onStart();
    actor.getBus().send(new RegisterMessage(this.getGroup(), actor));
  }
  
  public static ActorController configure(URL location) throws IOException, ReflectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    return configure(location, null);
  }
    
  public static ActorController configure(URL location, ResourceBundle resources) throws IOException, ReflectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    return configure(UUID.randomUUID(), location, resources, Collections.EMPTY_LIST);
  }

  public static ActorController configure(UUID group, URL location, ResourceBundle resources, List<?> params) throws IOException, ReflectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(location);
    loader.setResources(resources);
    Pane root = loader.load();
    
    ActorController controller = loader.getController();
    Stage stage = new Stage();
    Scene scene = new Scene(root);
    stage.setScene(scene);
  
    controller.setGroup(group);
    controller.setStage(stage);
    controller.setScene(scene);
    controller.setParams(params);
    
    controller.lateInitialize();
    return controller;
  }
  
  public void show() {
    this.getStage().toFront();
    this.getStage().show();
    getActor().send(new SplashShowMessage(false));
  }
  
  public void hide() {
    getActor().send(new SplashShowMessage(true));
    this.getStage().hide();
  }
  
  public void onClose() {
    Platform.runLater(() -> this.getStage().hide());
  }
  
  public abstract void start();
  
}
