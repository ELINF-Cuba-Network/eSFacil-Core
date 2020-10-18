package cu.vlired.esFacilCore.messageListener;

import com.github.sonus21.rqueue.annotation.RqueueListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class testMessageListener {

    @RqueueListener(value = "testing-queue")
    public void testing(String message) {
        log.info("Msg " + message);
    }

}
