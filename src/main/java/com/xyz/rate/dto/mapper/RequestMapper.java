package com.xyz.rate.dto.mapper;

public interface RequestMapper<R, E> {
    E mapFrom(R request);
}