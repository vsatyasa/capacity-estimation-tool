package com.stresstest;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.Instant;

public class MongoConnector {

    MongoDatabase dbObject;
    private final long snapShotId = Instant.now().getEpochSecond();

    public MongoConnector() {
        MongoClient client = new MongoClient("localhost" , 27017 );
        dbObject = client.getDatabase("stress-test");
        insertSnapshotDetails();
        System.out.println("Snapshot id : " + snapShotId);
    }

    private void insertSnapshotDetails() {
        Document document = new Document()
                .append("snapshotId", snapShotId)
                .append("numberOfUsers", UsersContext.NUM_OF_USERS)
                .append("spawnRate", UsersContext.SPAWN_RATE)
                .append("spawnGap", UsersContext.SPAWN_GAP)
                .append("timeout", APIContext.MAX_TIMEOUT_MS);

        MongoCollection<Document> collection = dbObject.getCollection("snapshot");
        collection.insertOne(document);
    }

    public void insertRequestLog(
            int threadId,
            String startTime,
            String endTime,
            Status requestStatus) {

        Document document = new Document()
                .append("snapshotId", snapShotId)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .append("threadId", threadId)
                .append("requestStatus", requestStatus.toString());
        MongoCollection<Document> collection = dbObject.getCollection("request-log");
        collection.insertOne(document);

    }


}
