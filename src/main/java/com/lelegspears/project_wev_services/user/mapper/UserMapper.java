package com.lelegspears.project_wev_services.user.mapper;

import com.lelegspears.project_wev_services.user.dtos.UserCreateDTO;
import com.lelegspears.project_wev_services.user.dtos.UserResponseDTO;
import com.lelegspears.project_wev_services.user.dtos.UserUpdateDTO;
import com.lelegspears.project_wev_services.user.entity.User;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDTO(User user);

    List<UserResponseDTO> toDTOList(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    User toEntity(UserCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "orders", ignore = true)
    void updateEntityFromDTO(UserUpdateDTO dto, @MappingTarget User user);
}