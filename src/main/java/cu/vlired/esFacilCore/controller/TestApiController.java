package cu.vlired.esFacilCore.controller;

import com.github.sonus21.rqueue.core.RqueueMessageEnqueuer;
import cu.vlired.esFacilCore.api.TestApi;
import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.model.SubmitTask;
import cu.vlired.esFacilCore.repository.BitstreamRepository;
import cu.vlired.esFacilCore.repository.DocumentRepository;
import cu.vlired.esFacilCore.repository.SubmitConfigRepository;
import cu.vlired.esFacilCore.services.BitstreamService;
import cu.vlired.esFacilCore.services.DspaceService;
import cu.vlired.esFacilCore.strategy.submit.SubmitContext;
import cu.vlired.esFacilCore.strategy.submit.SubmitStrategyFactory;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;

@Log4j2
@RestController
public class TestApiController implements TestApi {

    @Autowired
    SubmitStrategyFactory factory;

    final
    ResponsesHelper responseHelper;
    private final DspaceService dspaceService;
    private final DocumentRepository documentRepository;
    private final SubmitConfigRepository submitConfigRepository;
    private final BitstreamRepository bitstreamRepository;
    private final BitstreamService bitstreamService;
    private final RqueueMessageEnqueuer rqueueMessageEnqueuer;

    public TestApiController(
        ResponsesHelper responseHelper,
        DspaceService dspaceService,
        DocumentRepository documentRepository,
        SubmitConfigRepository submitConfigRepository,
        BitstreamRepository bitstreamRepository,
        BitstreamService bitstreamService,
        RqueueMessageEnqueuer rqueueMessageEnqueuer
    ) {
        this.responseHelper = responseHelper;
        this.dspaceService = dspaceService;
        this.documentRepository = documentRepository;
        this.submitConfigRepository = submitConfigRepository;
        this.bitstreamRepository = bitstreamRepository;
        this.bitstreamService = bitstreamService;
        this.rqueueMessageEnqueuer = rqueueMessageEnqueuer;
    }

    @Override
    @SneakyThrows
    public ResponseEntity<?> testing() {
        var c = submitConfigRepository.findById(UUID.fromString("095f1f5b-a86b-4af6-bb3b-d46160859c46"));

        var username = c.get().getData().getUsername();
        var password = c.get().getData().getPassword();
        var schema = c.get().getData().getSchema();
        var ip = c.get().getData().getIp();
        var port = c.get().getData().getPort();
        var endpoint = c.get().getData().getEndpoint();

        var url = String.format("%s://%s:%s/%s/login",
            schema,
            ip,
            port,
            endpoint
        );

        var cookie = dspaceService.login(username, password, url);

        var b = bitstreamRepository.findById(UUID.fromString("4e1a0709-a97f-4f78-bb8f-768cebd957f9"));
        var bitstream = b.get();

        url = String.format("%s://%s:%s/%s/items/%s/bitstreams?name=%s",
            schema,
            ip,
            port,
            endpoint,
            "34da249f-5b89-4cb7-9f8c-8de5969f4dca",
            bitstream.getName() + "." + bitstream.getExtension()
        );

        var path = bitstreamService.getPathByBitstreamCode(bitstream.getCode());
        var file = new File(path);

        log.info("File path " + file.getAbsolutePath());
        log.info("File exist? " + file.exists());
        log.info("File size? " + file.length());

        dspaceService.postBitstream(cookie, url, file);

        return responseHelper.okNoData();
    }

    @Override
    public ResponseEntity<?> testingQueue() {
        rqueueMessageEnqueuer.enqueue("testing-queue", "PONG");

        return responseHelper.okNoData();
    }

    @Override
    public ResponseEntity<?> testingStrategy() {
        var d = documentRepository.findById(UUID.fromString("97dea37a-334e-4769-b33c-1b4a4521db69")).get();
        var c = submitConfigRepository.findById(UUID.fromString("095f1f5b-a86b-4af6-bb3b-d46160859c46")).get();

        var task = new SubmitTask();
        task.setConfig(c);
        task.setDocument(d);

        var config = task.getConfig();

        var strategy = factory.getStrategy(config);
        var ctx = new SubmitContext(strategy);

        ctx.execute(task);

        return responseHelper.okNoData();
    }
}
