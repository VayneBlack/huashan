package com.guomin.service.user.provider.reponsitory.neo4j;

import com.guomin.service.user.provider.model.neo4j.NUser;
import com.guomin.service.user.provider.model.neo4j.UserRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface UserRelationRep extends Neo4jRepository<UserRelation,Long> {

    @Query("match (f:User),(s:User) where f.userId={firstUserId} and s.userId={secondUserId} create p=(f)-[r:UserRelation]->(s) return p")
    List<UserRelation> addUserRelation(@Param("firstUserId") Long firstUserId, @Param("secondUserId") Long secondUserId);

    @Query(value = "MATCH (u1:User{userId:{0}})-[:UserRelation*0..1]->(u2:User) return u2",
            countQuery = "MATCH (u1:User{userId:{0}})-[:UserRelation*0..1]->(u2:User) RETURN count(*)")
    Page<NUser> userRelationOneList(Long userId, Pageable pageable);

    @Query(value = "MATCH (u1:User{userId:{0}})-[:UserRelation*2]->(u2:User) return u2",
            countQuery = "MATCH (u1:User{userId:{0}})-[:UserRelation*2]->(u2:User) RETURN count(*)")
    Page<NUser> userRelationTwoList(Long userId, Pageable pageable);
}
