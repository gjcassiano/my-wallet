package com.wallet.mapper;

import com.wallet.dto.WalletDTO;
import com.wallet.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletMapper {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Mapping(target = "owner.password", ignore = true)
    WalletDTO toDTO(Wallet wallet);
    List<WalletDTO> toDTOs(List<Wallet> wallets);

    Wallet toEntity(WalletDTO walletDTO);

}
