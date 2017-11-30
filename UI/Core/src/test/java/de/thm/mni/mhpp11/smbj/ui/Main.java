package de.thm.mni.mhpp11.smbj.ui;

import de.thm.mni.mhpp11.smbj.logging.LogActorManagerPlugin;
import de.thm.mni.mhpp11.smbj.logging.actors.LogActor;
import de.thm.mni.mhpp11.smbj.logging.messages.WarnMessage;
import de.thm.mni.mhpp11.smbj.manager.ActorManager;
import de.thm.mni.mhpp11.smbj.manager.messages.SimpleStartMessage;
import de.thm.mni.mhpp11.smbj.ui.actors.TestActor;
import de.thm.mni.mhpp11.smbj.ui.messages.StartUIMessage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by hobbypunk on 12.01.17.
 */
public class Main extends Application {
  
  public static void main(String[] args) {
    launch(args);
  }
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    ActorManager manager = ActorManager.initialize(new LogActorManagerPlugin(), new UIActorManagerPlugin());
    manager.send(new SimpleStartMessage(LogActor.class));
//    manager.send(new StartUIMessage(SplashActor.class, new Stage()));
//    InitSplashMessage.InitSplashMessageBuilder builder = InitSplashMessage.builder();
//    builder.title("MoDE");
//    builder.subTitle("");
//    builder.version("0.7");
//    manager.send(builder.build());
    //manager.send(new ShowSplashMessage(null));
    new Thread(() -> {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
  
      manager.send(new StartUIMessage(TestActor.class, primaryStage));
  
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      ActorManager.getInstance().send(new WarnMessage(Main.class, "#"));
  //    ActorManager.getInstance().send(new ExitMessage(null));
    }).start();
  }
}