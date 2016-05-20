package provider;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Tatsuya Oba
 */
@Singleton
public class EbeanProvider implements Provider<EbeanServer> {

    @Override
    public EbeanServer get() {
        return Ebean.getServer("default");
    }
}
