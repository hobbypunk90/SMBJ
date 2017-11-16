package de.thm.mni.mhpp11.smbj.ui.splashscreen;

import de.thm.mni.mhpp11.smbj.actors.IActor;
import de.thm.mni.mhpp11.smbj.manager.ActorManagerPlugin;
import de.thm.mni.mhpp11.smbj.ui.splashscreen.actors.SplashActor;

public class SplashActorManagerPlugin extends ActorManagerPlugin{
  @Override
  protected Class<? extends IActor> isActorClass() {
    return SplashActor.class;
  }
}
