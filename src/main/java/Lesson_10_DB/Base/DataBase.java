package Lesson_10_DB.Base;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DataBase {

    ObjectWithData saveNewEntity(DbCollectionNames collectionName, UUID id, ObjectWithData value);

    ObjectWithData getEntity(DbCollectionNames collectionName, UUID id);

    List<ObjectWithData> getAllEntities(DbCollectionNames collectionName);

    ObjectWithData updateEntity(DbCollectionNames collectionName, UUID id, ObjectWithData value);

    List<ObjectWithData> updateMany(DbCollectionNames collectionName, Map<UUID, ObjectWithData> values);

    boolean isEntityExists(DbCollectionNames collectionName, UUID id);

    void deleteEntity(DbCollectionNames collectionName, UUID id);

//    method for look at state of DB, only for debug
    Map<DbCollectionNames, Map<UUID, ObjectWithData>> getData();

}
