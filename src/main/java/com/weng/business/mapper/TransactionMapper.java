package com.weng.business.mapper;

import com.weng.dto.ResultList;
import com.weng.dto.account.response.TransactionRes;
import com.weng.dto.account.response.TransferRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public abstract class TransactionMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "toHashId")
    abstract TransactionRes toTransactionSimpleRes(TransactionRes transactionRes);

    public ResponseEntity<TransactionRes> toTransactionRes(ResponseEntity<TransactionRes> responseEntity) {
        TransactionRes body = responseEntity.getBody();
        TransactionRes transformedBody = toTransactionSimpleRes(body);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(transformedBody);
    }

    public ResponseEntity<TransferRes> toTransferRes(ResponseEntity<TransferRes> responseEntity) {
        TransferRes body = responseEntity.getBody();
        body.setToTransaction(toTransactionSimpleRes(body.getToTransaction()));
        body.setFromTransaction(toTransactionSimpleRes(body.getFromTransaction()));
        return ResponseEntity.status(responseEntity.getStatusCode()).body(body);
    }

    public ResponseEntity<ResultList<TransactionRes>> toTransactionResList(ResponseEntity<ResultList<TransactionRes>> responseEntity) {
        // Transform each TransactionRes in the result list
        ResultList<TransactionRes> resultList = responseEntity.getBody();
        List<TransactionRes> transformedList = resultList.getResult().stream()
                .map(this::toTransactionSimpleRes)
                .toList();

        // Return a new ResultList with the transformed TransactionRes objects
        resultList = new ResultList<>(
                resultList.getTotal(),
                resultList.getCurrentPage() - 1, // Adjust back to original 0-based index
                resultList.getPageSize(),
                transformedList
        );
        return ResponseEntity.status(responseEntity.getStatusCode()).body(resultList);

    }
}
