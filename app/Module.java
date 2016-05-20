import com.avaje.ebean.EbeanServer;
import com.google.inject.AbstractModule;
import provider.EbeanProvider;

public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(EbeanServer.class).toProvider(EbeanProvider.class)
                .asEagerSingleton();
    }

}
