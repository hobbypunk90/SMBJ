package de.thm.mni.mhpp11.jActor;

import de.thm.mni.mhpp11.jActor.actors.logging.messages.ErrorMessage;
import de.thm.mni.mhpp11.jActor.actors.messagebus.MessageBus;
import de.thm.mni.mhpp11.jActor.actors.ui.messages.StartUIMessage;
import de.thm.mni.mhpp11.jActor.actors.ui.splash.messages.SplashInitMessage;
import de.thm.mni.mhpp11.jActor.actors.ui.splash.messages.SplashShowMessage;
import de.thm.mni.mhpp11.jActor.actors.utilities.Constants;
import de.thm.mni.mhpp11.jActor.ui.TestController;

/**
 * Created by hobbypunk on 12.01.17.
 */
public class Main {
  
  public static void main(String[] args) {
    MessageBus bus = MessageBus.getInstance();
    bus.onStart();
  
    SplashInitMessage.SplashInitMessageBuilder builder = SplashInitMessage.builder();
    builder.group(Constants.GLOBALGROUP).to(Constants.UI);
    builder.title("MoDE");
    builder.subTitle("The Modelica Diagram Editor");
    builder.version("0.5.1");
    bus.send(builder.build());
    bus.send(new SplashShowMessage(true));
  
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    bus.send(new StartUIMessage(TestController.class));
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    bus.send(new ErrorMessage(Main.class, new Exception("Hello World!")));
  }
}