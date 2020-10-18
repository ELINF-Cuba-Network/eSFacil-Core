package cu.vlired.esFacilCore.messageListener;

import com.github.sonus21.rqueue.annotation.RqueueListener;
import cu.vlired.esFacilCore.dto.queue.PostBitstreamDTO;
import cu.vlired.esFacilCore.model.Bitstream;
import cu.vlired.esFacilCore.model.SubmitTask;
import cu.vlired.esFacilCore.repository.BitstreamRepository;
import cu.vlired.esFacilCore.repository.SubmitTaskRepository;
import cu.vlired.esFacilCore.services.BitstreamService;
import cu.vlired.esFacilCore.services.DspaceService;
import cu.vlired.esFacilCore.util.DSpaceURLHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.File;
import java.util.UUID;

@Component
@Log4j2
public class PostBitstreamListener {

    @Autowired
    private BitstreamRepository bitstreamRepository;

    @Autowired
    private BitstreamService bitstreamService;

    @Autowired
    private SubmitTaskRepository submitTaskRepository;

    @Autowired
    private DspaceService dspaceService;

    @RqueueListener(value = "${app.queue.postBitstream.name}")
    public void postBitstream(PostBitstreamDTO dto) {

        log.debug("POST Bitstream, DTO: " + dto);

        UUID bitstreamId = dto.getBitstreamId();
        UUID taskId = dto.getTaskId();
        String itemId = dto.getItemID();
        String cookie = dto.getCookie();

        Bitstream bitstream = null;
        SubmitTask task = null;

        try {
            var bitOp = bitstreamRepository.findById(bitstreamId);
            var taskOp = submitTaskRepository.findById(taskId);

            if (bitOp.isPresent() && taskOp.isPresent()) {
                bitstream = bitOp.get();
                task = taskOp.get();

                var config = task.getConfig();

                var urlHelper = new DSpaceURLHelper(config.getData());
                var name = bitstream.getName() + "." + bitstream.getExtension();
                var url = urlHelper.buildPostBitstreamURL(itemId, name);

                log.info("POST Bitstream to " + url);

                var path = bitstreamService.getPathByBitstreamCode(bitstream.getCode());
                var file = new File(path);

                log.info("POST file " + file.getName());

                dspaceService.postBitstream(cookie, url, file);
            }
        } catch (Exception ex) {

            log.error("POST bitstream failed");
            log.error(ExceptionUtils.getStackTrace(ex));

            String message = "";

            if (ex instanceof HttpStatusCodeException) {
                message = ((HttpStatusCodeException) ex).getResponseBodyAsString();
            } else {
                message = ex.getMessage();
            }

            if (task != null) {
                task.setErrorMessage(task.getErrorMessage() + "\n" + message);
                submitTaskRepository.save(task);
            }
        }

    }

}
