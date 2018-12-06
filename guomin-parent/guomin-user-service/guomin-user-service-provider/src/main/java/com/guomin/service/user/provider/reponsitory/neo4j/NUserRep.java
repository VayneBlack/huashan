package com.guomin.service.user.provider.reponsitory.neo4j;

import com.guomin.service.user.provider.model.neo4j.NUser;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;


@Component
public interface NUserRep extends Neo4jRepository<NUser,Long> {
    NUser findByUserId(Long userId);
    Long deleteUserByUserId(Long userId);
}
