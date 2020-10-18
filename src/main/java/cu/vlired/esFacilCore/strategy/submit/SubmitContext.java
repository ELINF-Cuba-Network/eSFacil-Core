package cu.vlired.esFacilCore.strategy.submit;

import cu.vlired.esFacilCore.model.SubmitTask;

public class SubmitContext {
    private SubmitStrategy strategy;

    public SubmitContext(SubmitStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(SubmitTask submitTask) {
        strategy.submit(submitTask);
    }
}
