package com.railway.booking.mapper;

public interface Mapper<E, D> {
    E mapDomainToEntity(D item);

    D mapEntityToDomain(E entity);
}