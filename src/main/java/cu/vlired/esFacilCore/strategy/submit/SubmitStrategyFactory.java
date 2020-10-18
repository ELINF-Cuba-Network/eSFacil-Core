package cu.vlired.esFacilCore.strategy.submit;

import cu.vlired.esFacilCore.model.SubmitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubmitStrategyFactory {

    @Autowired
    DSpaceSubmitStrategy dSpaceSubmitStrategy;

    public SubmitStrategy getStrategy(SubmitConfig config) {
        switch (config.getType()) {
            case SubmitConfig.TYPE_DSPACE:
                return dSpaceSubmitStrategy;
            default:
                return dSpaceSubmitStrategy;
        }
    }

}
