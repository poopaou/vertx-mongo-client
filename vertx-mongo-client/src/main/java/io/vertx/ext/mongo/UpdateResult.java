package io.vertx.ext.mongo;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * The result of an update operation.  If the update was unacknowledged, then {@code wasAcknowledged} will return false.
 *
 * @see com.mongodb.WriteConcern#UNACKNOWLEDGED
 * @since 3.0
 */
@DataObject
public class UpdateResult {

    private final boolean acknowledged;
    private final Long matchedCount;
    private final Long modifiedCount;
    private final String upsertedId;

    public UpdateResult(boolean acknowledged, Long matchedCount, Long modifiedCount, String upsertedId) {
        this.acknowledged = acknowledged;
        this.matchedCount = matchedCount;
        this.modifiedCount = modifiedCount;
        this.upsertedId = upsertedId;
    }

    public UpdateResult(JsonObject json) {
        this.acknowledged = json.getBoolean("acknowledged", false);
        this.matchedCount = json.getLong("matchedCount");
        this.modifiedCount = json.getLong("modifiedCount");
        this.upsertedId = json.getString("upsertedId");
    }

    /**
     * Convert to JSON
     *
     * @return  the JSON
     */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put("acknowledged", acknowledged);
        if (matchedCount != null) {
            json.put("matchedCount", matchedCount);
        }
        if (modifiedCount != null) {
            json.put("modifiedCount", modifiedCount);
        }
        if (upsertedId != null) {
            json.put("upsertedId", upsertedId);
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
     * Gets the number of documents matched by the query.
     *
     * @return the number of documents matched
     */
    public Long getMatchedCount(){
        return matchedCount;
    }

    /**
     * Gets a value indicating whether the modified count is available.
     * <p>
     * The modified count is only available when all servers have been upgraded to 2.6 or above.
     * </p>
     *
     * @return true if the modified count is available
     */
    public boolean isModifiedCountAvailable() {
        return modifiedCount != null;
    }

    /**
     * Gets the number of documents modified by the update.
     *
     * @return the number of documents modified
     */
    public Long getModifiedCount(){
        return modifiedCount;
    }

    /**
     * If the replace resulted in an inserted document, gets the _id of the inserted document, otherwise null.
     *
     * @return if the replace resulted in an inserted document, the _id of the inserted document, otherwise null
     */
    public String getUpsertedId(){
        return upsertedId;
    }

}
