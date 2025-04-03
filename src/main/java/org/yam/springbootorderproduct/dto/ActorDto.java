package org.yam.springbootorderproduct.dto;

import lombok.Data;
import org.yam.springbootorderproduct.model.ActorRole;

@Data
public class ActorDto {
    private String mail;
    private String password;
    private Enum<ActorRole> actorRole;
}