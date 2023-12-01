package ch.sbb.ki.geoquest.backend.mapper;

import ch.sbb.ki.geoquest.backend.controller.dto.UserDTO;
import ch.sbb.ki.geoquest.backend.persistence.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO map(User user);

    List<UserDTO> map(List<User> users);
}