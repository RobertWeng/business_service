package com.weng.business.mapper;

import com.weng.dto.account.response.FundAccountRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.ResponseEntity;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public abstract class FundAccountMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "toHashId")
    abstract FundAccountRes toFundAccountSimpleRes(FundAccountRes fundAccountRes);

    public ResponseEntity<FundAccountRes> toFundAccountRes(ResponseEntity<FundAccountRes> responseEntity) {
        FundAccountRes body = responseEntity.getBody();
        FundAccountRes transformedBody = toFundAccountSimpleRes(body);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(transformedBody);
    }
}
