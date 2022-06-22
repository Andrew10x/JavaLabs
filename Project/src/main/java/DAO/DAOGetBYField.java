package DAO;

import java.util.List;

public interface DAOGetBYField<Entity, Field> {
    Entity get(Field k);
}

interface DAOAdd<Entity> {
    int add(Entity e);
}

interface DAOGetList<Entity> {
    List<Entity> getAll();
}

interface DAOGetOrdersListWithFilter<Entity> {
    List<Entity> getAllWithFilter(int id, String email, String status, String date,
                                  String cityFrom, String cityTo);
}

interface DAOGetById<Entity, IdKey> {
    Entity getById(IdKey id);
}

interface DAOUpdate<Entity> {
    int update(Entity e);
}

interface DAOGetByTwoFields<Entity, Field1, Field2> {
    Entity getByTwoFields(Field1 f1, Field2 f2);
}







