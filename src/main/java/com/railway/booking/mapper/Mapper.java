package com.railway.booking.mapper;

public interface Mapper<E, D> {
    E mapEntityToModel(D item);

    D mapModelToEntity(E entity);
}