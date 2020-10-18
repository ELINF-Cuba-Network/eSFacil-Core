package cu.vlired.esFacilCore.util;

import cu.vlired.esFacilCore.model.configFormData.SubmitConfigData;

public class DSpaceURLHelper {

    private String schema;
    private String ip;
    private String endpoint;
    private String port;
    private String collectionId;

    public DSpaceURLHelper(SubmitConfigData configData) {
        this.schema = configData.getSchema();
        this.ip = configData.getIp();
        this.endpoint = configData.getEndpoint();
        this.port = configData.getPort();
        this.collectionId = configData.getCollectionId();
    }

    public String buildLoginURL() {
        return String.format("%s://%s:%s/%s/login",
            schema,
            ip,
            port,
            endpoint
        );
    }

    public String buildPostItemURL() {
        return String.format("%s://%s:%s/%s/collections/%s/items",
            schema,
            ip,
            port,
            endpoint,
            collectionId
        );
    }

    public String buildPostBitstreamURL(String itemId, String name) {
        return String.format("%s://%s:%s/%s/items/%s/bitstreams?name=%s",
            schema,
            ip,
            port,
            endpoint,
            itemId,
            name
        );
    }
}
