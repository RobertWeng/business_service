package com.weng.business.mapper;

import com.weng.dto.user.response.LoginRes;
import com.weng.dto.user.response.UserRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.ResponseEntity;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public abstract class UserMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "toHashId")
    abstract UserRes toUserSimpleRes(UserRes userRes);

    @Mapping(source = "id", target = "id", qualifiedByName = "toHashId")
    abstract LoginRes toLoginSimpleRes(LoginRes loginRes);

    public ResponseEntity<UserRes> toUserRes(ResponseEntity<UserRes> responseEntity) {
        UserRes body = responseEntity.getBody();
        UserRes transformedBody = toUserSimpleRes(body);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(transformedBody);
    }

    public ResponseEntity<LoginRes> toLoginRes(ResponseEntity<LoginRes> responseEntity) {
        LoginRes body = responseEntity.getBody();
        LoginRes transformedBody = toLoginSimpleRes(body);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(transformedBody);
    }
}
