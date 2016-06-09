package io.vertx.ext.mongo;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by cysondag on 07/06/2016.
 */
@DataObject
public class DeleteResult {

    private final boolean acknowledged;
    private final Long deletedCount;

    public DeleteResult(boolean acknowledged, Long deletedCount) {
        this.acknowledged = acknowledged;
        this.deletedCount = deletedCount;
    }

    public DeleteResult(JsonObject json) {
        this.acknowledged = json.getBoolean("acknowledged", false);
        this.deletedCount = json.getLong("deletedCount");
    }

    /**
     * Convert to JSON
     *
     * @return  the JSON
     */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put("acknowledged", acknowledged);
        if (deletedCount != null) {
            json.put("deletedCount", deletedCount);
        }
        return json;
    }

    /**
     * Returns true if the write was acknowledged.
     *
     * @return true if the write was acknowledged
     */
    public boolean wasAcknowledged() {
        return acknowledged;
    }

    /**
     * Gets the number of documents deleted.
     *
     * @return the number of documents deleted
     */
    public Long getDeletedCount(){
        return deletedCount;
    }
}
