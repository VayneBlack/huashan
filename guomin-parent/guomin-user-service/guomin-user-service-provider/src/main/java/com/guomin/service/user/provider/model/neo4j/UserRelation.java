package com.guomin.service.user.provider.model.neo4j;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@RelationshipEntity(type = "UserRelation")
public class UserRelation {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private NUser recommended;

    @EndNode
    @Index(unique = true)
    private NUser referee;

}
