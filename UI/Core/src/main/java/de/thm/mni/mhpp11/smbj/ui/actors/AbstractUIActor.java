package de.thm.mni.mhpp11.smbj.ui.actors;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.logging.messages.ErrorMessage;
import de.thm.mni.mhpp11.smbj.manager.messages.InitMessage;
import de.thm.mni.mhpp11.smbj.messages.ErrorExitMessage;
import de.thm.mni.mhpp11.smbj.messages.ExitMessage;
import de.thm.mni.mhpp11.smbj.messages.base.Message;
import de.thm.mni.mhpp11.smbj.ui.controllers.ActorController;
import de.thm.mni.mhpp11.smbj.ui.utilities.UTF8ResourceBundleControl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hobbypunk on 18.01.17.
 */
@Data
@FieldDefaults(makeFinal = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractUIActor<C extends ActorController> extends Task<Message> implements IActor {
  
  BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
  
  UUID ID;
  Stage stage;
  private final List<?> params;
  @NonFinal private C controller;
  
  protected AbstractUIActor(UUID id, Stage stage) {
    this(id, stage, Collections.EMPTY_LIST);
  }
  
  protected AbstractUIActor(UUID id, Stage stage, List<?> params) {
    this.ID = id;
    this.stage = stage;
    this.params = Collections.unmodifiableList(params);
  }
  
  public String getViewPath() {
    return "/" + getClass().getPackage().getName().replaceAll("\\.", "/").replaceAll("actors$", "views") +
               "/" + getClass().getSimpleName().replaceAll("Actor$", "View") + ".fxml";
  }
  
  public String getBundlePath() {
    return getClass().getPackage().getName().replaceAll("\\.", "/").replaceAll("actors$", "bundles") +
        "/" + getClass().getSimpleName().replaceAll("Actor$", "Bundle");
  }
  
  protected Locale getLocale() {
    return Locale.getDefault();
  }
  
  private void init() {
    try {
      FXMLLoader loader = new FXMLLoader();
      URL fxml = AbstractUIActor.class.getResource(getViewPath());
      if (fxml == null) throw new MissingResourceException("FXML not found!", getClass().getSimpleName(), getViewPath());
      
      loader.setLocation(fxml);
      if(getBundlePath() != null && !getBundlePath().isEmpty()) {
        try {
          ResourceBundle bundle = ResourceBundle.getBundle(getBundlePath(), getLocale(), new UTF8ResourceBundleControl());
          loader.setResources(bundle);
        } catch (MissingResourceException e) {
          send(new ErrorMessage(this.getClass(), getID(), e));
        }
      }
      
      Pane root = loader.load();
  
      C controller = loader.getController();
      controller.setParams(params);
      Scene scene = new Scene(root);
      stage.setScene(scene);
      controller.setStage(stage);
      
      this.setController(controller);
      controller.setActor(this);
      controller.setScene(scene);
      controller.lateInitialize();
      afterInit();
    } catch(IOException e) {
      e.printStackTrace();
      send(new ErrorMessage(this.getClass(), getID(), e));
      send(new ErrorExitMessage(getID()));
    }
  }
  
  protected void afterInit() {}
  
  @Override
  @SuppressWarnings("unchecked")
  public final void execute(Message msg) {
    if (msg instanceof InitMessage) {
      Platform.runLater(AbstractUIActor.this::init);
    } else {
      if (msg instanceof ExitMessage) {
        Platform.runLater(this.stage::hide);
      }
      Platform.runLater(() -> executeUI(msg));
    }
  }
  
  public abstract void executeUI(Message msg);
  
  @Override
  protected Message call() throws Exception {
    IActor.super.run();
    return null;
  }
  
  @Override
  public void onStop(Message lastMsg) {
    Platform.runLater(() -> getController().onClose());
  }
}
