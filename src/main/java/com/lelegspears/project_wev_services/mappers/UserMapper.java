package com.lelegspears.project_wev_services.mappers;

import com.lelegspears.project_wev_services.dtos.UserCreateDTO;
import com.lelegspears.project_wev_services.dtos.UserResponseDTO;
import com.lelegspears.project_wev_services.dtos.UserUpdateDTO;
import com.lelegspears.project_wev_services.entities.User;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDTO(User user);

    List<UserResponseDTO> toDTOList(List<User> users);

    User toEntity(UserCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDTO(UserUpdateDTO dto, @MappingTarget User user);
}