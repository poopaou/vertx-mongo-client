package io.vertx.ext.mongo;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.impl.MongoClientImpl;

import java.util.List;
import java.util.UUID;

/**
 * A Vert.x service used to interact with MongoDB server instances.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@VertxGen
public interface MongoClient {

  /**
   * The name of the default pool
   */
  String DEFAULT_POOL_NAME = "DEFAULT_POOL";

  /**
   * The name of the default database
   */
  String DEFAULT_DB_NAME = "DEFAULT_DB";

  /**
   * Create a Mongo client which maintains its own data source.
   *
   * @param vertx  the Vert.x instance
   * @param config  the configuration
   * @return the client
   */
  static MongoClient createNonShared(Vertx vertx, JsonObject config) {
    return new MongoClientImpl(vertx, config, UUID.randomUUID().toString());
  }

  /**
   * Create a Mongo client which shares its data source with any other Mongo clients created with the same
   * data source name
   *
   * @param vertx  the Vert.x instance
   * @param config  the configuration
   * @param dataSourceName  the data source name
   * @return the client
   */
  static MongoClient createShared(Vertx vertx, JsonObject config, String dataSourceName) {
    return new MongoClientImpl(vertx, config, dataSourceName);
  }

  /**
   * Like {@link #createShared(io.vertx.core.Vertx, JsonObject, String)} but with the default data source name
   * @param vertx  the Vert.x instance
   * @param config  the configuration
   * @return the client
   */
  static MongoClient createShared(Vertx vertx, JsonObject config) {
    return new MongoClientImpl(vertx, config, DEFAULT_POOL_NAME);
  }


  /**
   * Save a document in the specified collection
   *
   * @param collection  the collection
   * @param document  the document
   * @param resultHandler  result handler will be provided with the id if document didn't already have one
   */
  @Fluent
  MongoClient save(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler);

  /**
   * Save a document in the specified collection with the specified write option
   *
   * @param collection  the collection
   * @param document  the document
   * @param writeOption  the write option to use
   * @param resultHandler  result handler will be provided with the id if document didn't already have one
   */
  @Fluent
  MongoClient saveWithOptions(String collection, JsonObject document, WriteOption writeOption, Handler<AsyncResult<String>> resultHandler);

  /**
   * Insert a document in the specified collection
   *
   * @param collection  the collection
   * @param document  the document
   * @param resultHandler  result handler will be provided with the id if document didn't already have one
   */
  @Fluent
  MongoClient insert(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler);

  /**
   * Insert a document in the specified collection with the specified write option
   *
   * @param collection  the collection
   * @param document  the document
   * @param writeOption  the write option to use
   * @param resultHandler  result handler will be provided with the id if document didn't already have one
   */
  @Fluent
  MongoClient insertWithOptions(String collection, JsonObject document, WriteOption writeOption, Handler<AsyncResult<String>> resultHandler);

  /**
   * Update matching documents in the specified collection
   *
   * @param collection  the collection
   * @param query  query used to match the documents
   * @param update used to describe how the documents will be updated
   * @param resultHandler will be called when complete
   */
  @Fluent
  MongoClient update(String collection, JsonObject query, JsonObject update, Handler<AsyncResult<UpdateResult>> resultHandler);

  /**
   * Update matching documents in the specified collection, specifying options
   *
   * @param collection  the collection
   * @param query  query used to match the documents
   * @param update used to describe how the documents will be updated
   * @param options options to configure the update
   * @param resultHandler will be called when complete
   */
  @Fluent
  MongoClient updateWithOptions(String collection, JsonObject query, JsonObject update, UpdateOptions options, Handler<AsyncResult<UpdateResult>> resultHandler);

  /**
   * Replace matching documents in the specified collection
   *
   * @param collection  the collection
   * @param query  query used to match the documents
   * @param replace  all matching documents will be replaced with this
   * @param resultHandler will be called when complete
   */
  @Fluent
  MongoClient replace(String collection, JsonObject query, JsonObject replace, Handler<AsyncResult<UpdateResult>> resultHandler);

  /**
   * Replace matching documents in the specified collection, specifying options
   *
   * @param collection  the collection
   * @param query  query used to match the documents
   * @param replace  all matching documents will be replaced with this
   * @param options options to configure the replace
   * @param resultHandler will be called when complete
   */
  @Fluent
  MongoClient replaceWithOptions(String collection, JsonObject query, JsonObject replace, UpdateOptions options, Handler<AsyncResult<UpdateResult>> resultHandler);

  /**
   * Find matching documents in the specified collection
   *
   * @param collection  the collection
   * @param query  query used to match documents
   * @param resultHandler  will be provided with list of documents
   */
  @Fluent
  MongoClient find(String collection, JsonObject query, Handler<AsyncResult<List<JsonObject>>> resultHandler);

  /**
   * Find matching documents in the specified collection.
   * This method use batchCursor for returning each found document.
   *
   * @param collection  the collection
   * @param query  query used to match documents
   * @param resultHandler  will be provided with each found document
   */
  @Fluent
  MongoClient findBatch(String collection, JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler);

  /**
   * Find matching documents in the specified collection, specifying options
   *
   * @param collection  the collection
   * @param query  query used to match documents
   * @param options options to configure the find
   * @param resultHandler  will be provided with list of documents
   */
  @Fluent
  MongoClient findWithOptions(String collection, JsonObject query, FindOptions options, Handler<AsyncResult<List<JsonObject>>> resultHandler);

  /**
   * Find matching documents in the specified collection, specifying options.
   * This method use batchCursor for returning each found document.
   *
   * @param collection  the collection
   * @param query  query used to match documents
   * @param options options to configure the find
   * @param resultHandler  will be provided with each found document
   */
  @Fluent
  MongoClient findBatchWithOptions(String collection, JsonObject query, FindOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

  /**
   * Find a single matching document in the specified collection
   *
   * @param collection  the collection
   * @param query  the query used to match the document
   * @param fields  the fields
   * @param resultHandler will be provided with the document, if any
   */
  @Fluent
  MongoClient findOne(String collection, JsonObject query, JsonObject fields, Handler<AsyncResult<JsonObject>> resultHandler);

  /**
   * Count matching documents in a collection.
   *
   * @param collection  the collection
   * @param query  query used to match documents
   * @param resultHandler will be provided with the number of matching documents
   */
  @Fluent
  MongoClient count(String collection, JsonObject query, Handler<AsyncResult<Long>> resultHandler);

  /**
   * Remove matching documents from a collection
   *
   * @param collection  the collection
   * @param query  query used to match documents
   * @param resultHandler will be called when complete
   */
  @Fluent
  MongoClient remove(String collection, JsonObject query, Handler<AsyncResult<DeleteResult>> resultHandler);

  /**
   * Remove matching documents from a collection with the specified write option
   *
   * @param collection  the collection
   * @param query  query used to match documents
   * @param writeOption  the write option to use
   * @param resultHandler will be called when complete
   */
  @Fluent
  MongoClient removeWithOptions(String collection, JsonObject query, WriteOption writeOption, Handler<AsyncResult<DeleteResult>> resultHandler);

  /**
   * Remove a single matching document from a collection
   *
   * @param collection  the collection
   * @param query  query used to match document
   * @param resultHandler will be called when complete
   */
  @Fluent
  MongoClient removeOne(String collection, JsonObject query, Handler<AsyncResult<DeleteResult>> resultHandler);

  /**
   * Remove a single matching document from a collection with the specified write option
   *
   * @param collection  the collection
   * @param query  query used to match document
   * @param writeOption  the write option to use
   * @param resultHandler will be called when complete
   */
  @Fluent
  MongoClient removeOneWithOptions(String collection, JsonObject query, WriteOption writeOption, Handler<AsyncResult<DeleteResult>> resultHandler);

  /**
   * Create a new collection
   *
   * @param collectionName  the name of the collection
   * @param resultHandler  will be called when complete
   */
  @Fluent
  MongoClient createCollection(String collectionName, Handler<AsyncResult<Void>> resultHandler);

  /**
   * Get a list of all collections in the database.
   *
   * @param resultHandler  will be called with a list of collections.
   */
  @Fluent
  MongoClient getCollections(Handler<AsyncResult<List<String>>> resultHandler);

  /**
   * Drop a collection
   *
   * @param collection  the collection
   * @param resultHandler will be called when complete
   */
  @Fluent
  MongoClient dropCollection(String collection, Handler<AsyncResult<Void>> resultHandler);

  /**
   * Run an arbitrary MongoDB command.
   *
   * @param commandName  the name of the command
   * @param command  the command
   * @param resultHandler  will be called with the result.
   */
  @Fluent
  MongoClient runCommand(String commandName, JsonObject command, Handler<AsyncResult<JsonObject>> resultHandler);

  /**
   * Gets the distinct values of the specified field name.
   * Return a JsonArray containing distinct values (eg: [ 1 , 89 ])
   *
   * @param collection  the collection
   * @param fieldName  the field name
   * @param resultHandler  will be provided with array of values.
   */
  @Fluent
  MongoClient distinct(String collection, String fieldName, String resultClassname, Handler<AsyncResult<JsonArray>> resultHandler);

  /**
   * Gets the distinct values of the specified field name.
   * This method use batchCursor for returning each found value.
   * Each value is a json fragment with fieldName key (eg: {"num": 1}).
   *
   * @param collection  the collection
   * @param fieldName  the field name
   * @param resultHandler  will be provided with each found value
   */
  @Fluent
  MongoClient distinctBatch(String collection, String fieldName, String resultClassname, Handler<AsyncResult<JsonObject>> resultHandler);

  /**
   * Close the client and release its resources
   */
  void close();

}
