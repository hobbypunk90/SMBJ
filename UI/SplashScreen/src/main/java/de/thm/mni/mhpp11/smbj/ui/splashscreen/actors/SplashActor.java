package de.thm.mni.mhpp11.smbj.ui.splashscreen.actors;

import de.thm.mni.mhpp11.smbj.manager.messages.StartedMessage;
import de.thm.mni.mhpp11.smbj.messages.base.Message;
import de.thm.mni.mhpp11.smbj.ui.actors.AbstractUIActor;
import de.thm.mni.mhpp11.smbj.ui.messages.HideStageMessage;
import de.thm.mni.mhpp11.smbj.ui.messages.ShowStageMessage;
import de.thm.mni.mhpp11.smbj.ui.splashscreen.controllers.SplashController;
import de.thm.mni.mhpp11.smbj.ui.splashscreen.messages.InitSplashMessage;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.UUID;

public class SplashActor extends AbstractUIActor<SplashController> {
  
  public SplashActor(UUID id, Stage stage, List<?> params) {
    super(id, stage, params);
  }
  
  @Override
  public String getBundlePath() {
    return null;
  }
  
  @Override
  protected void afterInit() {
    this.getStage().initStyle(StageStyle.UNDECORATED);
    this.getStage().setAlwaysOnTop(true);
    this.getStage().centerOnScreen();
    this.getStage().setResizable(false);
  }
  
  private void showSplash(Boolean value) {
    if (value != null) {
      Platform.runLater(() -> {
        if (value) {
          this.getStage().show();
        } else {
          this.getStage().hide();
        }
      });
    }
  }
  
  @Override
  public void executeUI(Message msg) {
    if(msg instanceof StartedMessage) {
      getController().initSplash();
    }else if(msg instanceof InitSplashMessage) {
      getController().initSplash((InitSplashMessage) msg);
    } else if (msg instanceof ShowStageMessage) {
      showSplash(false);
    } else if (msg instanceof HideStageMessage) {
      showSplash(true);
    }
  }
}