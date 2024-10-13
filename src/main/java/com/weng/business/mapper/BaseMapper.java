package com.weng.business.mapper;

import com.weng.business.util.HashId;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Mapper(componentModel = "spring")
public interface BaseMapper {
    @Named("toHashId")
    default String toHashId(String id) {
        if (isBlank(id)) return null;
        return HashId.encode(Long.parseLong(id));
    }
}
