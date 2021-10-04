package com.example.file.storage.mapper;

public interface IMapper<T, U> {

    U map(T model);
}
