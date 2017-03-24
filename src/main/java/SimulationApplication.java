import Services.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Singleton
@ApplicationPath("/api")
public class SimulationApplication extends Application {

    @Inject
    private UserService userService;

    @PostConstruct
    public void initData(){

    }
}
