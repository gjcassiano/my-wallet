package com.picpay.wallet.mapper;

import com.picpay.wallet.dto.UserDTO;
import com.picpay.wallet.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(User user);
    List<UserDTO> toDTOs(List<User> users);

    User toEntity(UserDTO userDTO);
}
