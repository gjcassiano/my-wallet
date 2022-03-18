package com.wallet.mapper;

import com.wallet.dto.BaseEntityDTO;
import com.wallet.entities.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;



/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Mapper(componentModel = "spring")
public interface BaseMapper {

    BaseMapper INSTANCE = Mappers.getMapper(BaseMapper.class);

    BaseEntityDTO toDTO(BaseEntity baseEntity);


}
