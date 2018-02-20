package org.superbiz;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple CDI @Startup replacement
 */
@ApplicationScoped
public class MyAppScoped {

    public void init(@Observes @Initialized(ApplicationScoped.class) final Object o) {
        //Make a mess
        Logger.getAnonymousLogger().log(Level.SEVERE, "Not an error. I just wanted to give you a SHOUT!");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) final Object o) {
        //Cleanup
        Logger.getAnonymousLogger().log(Level.SEVERE, "Not an error. You can go back to sleep!");
    }
}
