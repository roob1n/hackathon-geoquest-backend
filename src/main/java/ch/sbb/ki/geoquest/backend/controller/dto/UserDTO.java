package ch.sbb.ki.geoquest.backend.controller.dto;

import lombok.Value;

@Value
public class UserDTO {
    Long id;
    String userName;
    Boolean isAdmin;
    Integer score;
}
