package com.wallet.mapper;

import com.wallet.dto.TransactionDTO;
import com.wallet.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO toDTO(Transaction transaction);
    List<TransactionDTO> toDTOs(List<Transaction> transactions);

    Transaction toEntity(TransactionDTO transactionDTO);

}
