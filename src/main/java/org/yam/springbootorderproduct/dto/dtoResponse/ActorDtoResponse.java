package org.yam.springbootorderproduct.dto.dtoResponse;

import lombok.Data;
import org.yam.springbootorderproduct.model.ActorRole;

@Data
public class ActorDtoResponse {
    private String mail;
    private String password;
    private Enum<ActorRole> actorRole;
}