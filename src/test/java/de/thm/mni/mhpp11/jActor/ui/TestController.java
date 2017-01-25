package de.thm.mni.mhpp11.jActor.ui;

import de.thm.mni.mhpp11.jActor.actors.logging.messages.ErrorMessage;
import de.thm.mni.mhpp11.jActor.actors.logging.messages.LogMessage;
import de.thm.mni.mhpp11.jActor.actors.logging.messages.WarnMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.MessageBus;
import de.thm.mni.mhpp11.jActor.actors.ui.interfaces.AbstractUIActor;
import de.thm.mni.mhpp11.jActor.actors.ui.interfaces.ActorController;
import de.thm.mni.mhpp11.jActor.actors.ui.splash.messages.SplashShowMessage;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.messages.interfaces.Message;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import lombok.Getter;

import javax.management.ReflectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by hobbypunk on 16.01.17.
 */
@Getter
public class TestController extends ActorController {
  private static String basedir = "/de/thm/mni/mhpp11/jActor/test/view";
  
  private Stage stage;
  @FXML TextArea textArea;
  
  public TestController() {
    this.setGroup(UUID.randomUUID());
  }
  
  public TestController(UUID group) {
    this.setGroup(group);
  }
  
  public static void configure() throws IOException, ReflectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    ActorController controller = configure(TestController.class.getResource(basedir + "/test.fxml"));
    controller.start();
  }
  
  public static void configure(UUID group) throws IOException, ReflectionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    ActorController controller = configure(group, TestController.class.getResource(basedir + "/test.fxml"), null, Collections.EMPTY_LIST);
    controller.start();
  }
  
  @FXML
  public void onOpenNew() {
    try {
      TestController.configure(getGroup());
    } catch (Exception e) {
      MessageBus.getInstance().send(new ErrorMessage(this.getClass(), e));
    }
  }
  
  @FXML
  public void onPrint() {
    MessageBus.getInstance().send(new WarnMessage(this.getClass(), Constants.ALLGROUPS, "Hello World!"));
  }
  
  @Override
  public void start() {
    show();
    getActor().send(new SplashShowMessage(false));
  }
  
  private static class TestWindowActor extends AbstractUIActor<TestController, Message> {
  
    private TestWindowActor(TestController controller) {
      super(controller);
    }
    
    @Override
    public void executeUI(Message msg) {
      if(msg instanceof LogMessage) {
        this.getController().textArea.setText(this.getController().textArea.getText() + "\n" + ((LogMessage) msg).getMessage());
      }
    }
  }
}
