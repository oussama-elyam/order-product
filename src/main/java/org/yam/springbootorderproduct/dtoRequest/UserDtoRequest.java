package org.yam.springbootorderproduct.dtoRequest;

import lombok.Data;
import org.yam.springbootorderproduct.model.ActorRole;

@Data
public class UserDtoRequest {
    private String mail;
    private String password;
    private Enum<ActorRole> actorRole;
}
