package com.weng.business.mapper;

import com.weng.dto.account.response.TransactionRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.ResponseEntity;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public abstract class TransactionMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "toHashId")
    abstract TransactionRes toTransactionSimpleRes(TransactionRes transactionRes);

    public ResponseEntity<TransactionRes> toFundAccountRes(ResponseEntity<TransactionRes> responseEntity) {
        TransactionRes body = responseEntity.getBody();
        TransactionRes transformedBody = toTransactionSimpleRes(body);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(transformedBody);
    }
}
