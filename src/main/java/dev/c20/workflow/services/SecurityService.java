package dev.c20.workflow.services;

import dev.c20.workflow.entities.Storage;
import dev.c20.workflow.entities.adds.Perm;
import dev.c20.workflow.tools.PathUtils;
import dev.c20.workflow.tools.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SecurityService {

    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    private HttpServletResponse httpResponse = null;
    private HttpServletRequest httpRequest = null;
    private Storage requestedStorage  = null;
    private Map<String,Object> userData = null;
    private String path = null;

    EntityManager em;
    @Autowired
    public SecurityService setEntityManager( EntityManager em) {
        logger.info( "Set entityManager");
        this.em = em;
        return this;
    }


    public SecurityService setHttpServletResponse( HttpServletResponse httpResponse ) {
        this.httpResponse = httpResponse;
        return this;
    }
    public SecurityService setHttpServletRequest( HttpServletRequest httpRequest ) {
        this.httpRequest  = httpRequest;
        return this;
    }

    public Storage getRequestedStorage() {
        return requestedStorage;
    }

    public SecurityService setRequestedStorage(Storage requestedStorage) {
        this.requestedStorage = requestedStorage;
        return this;
    }

    public Map<String,Object> getUserData() {
        return userData;
    }

    public SecurityService setUserData(Map<String,Object> user) {
        this.userData = user;
        return this;
    }

    public boolean canCreate() {
        return hasPermition( true, null, null, null, true, false);
    }

    public boolean canRead() {
        return hasPermition( false, true, null, null, true, false);
    }

    public boolean canUpdate() {
        return hasPermition( false, false, true, null, true, false);
    }

    public boolean canDelete( ) {
        return hasPermition(false, false, false, true, true, false);
    }

    public boolean canAdmin() {
        return hasPermition(false, null, null, null, true, false);
    }

    public boolean canSend() {
        return hasPermition(false, null, null, null, true, true);
    }


    public boolean hasPermition(Boolean canCreate, Boolean canRead, Boolean canUpdate, Boolean canDelete, Boolean canAdmin, Boolean canSend) {

        if( this.getUserData() == null )
            return false;

        if( !this.requestedStorage.getRestrictedByPerm() )
            return true;

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<Perm> perm = cq.from(Perm.class);
        Path<Boolean> userPath = perm.get("user");
        Path<Boolean> parentPath = perm.get("parent");
        Path<Boolean> createPath = perm.get("create");
        Path<Boolean> readPath   = perm.get("read");
        Path<Boolean> updatePath = perm.get("update");
        Path<Boolean> deletePath = perm.get("delete");
        Path<Boolean> adminPath  = perm.get("admin");
        Path<Boolean> sendPath  = perm.get("send");


        cq.select(qb.count(cq.from(Perm.class)));

        List<Predicate> predicates = new ArrayList<>();

        predicates.add( qb.and( qb.equal(parentPath,requestedStorage)) );
        predicates.add( qb.and( qb.equal(userPath,(String)userData.get("user"))) );

        if( canCreate != null )
            predicates.add( qb.and( qb.equal(createPath,canCreate)) );

        if( canRead != null )
            predicates.add( qb.and( qb.equal(readPath,canRead)) );

        if( canUpdate != null )
            predicates.add( qb.and( qb.equal(updatePath,canUpdate)) );

        if( canDelete != null )
            predicates.add( qb.and( qb.equal(deletePath,canDelete)) );

        if( canAdmin != null )
            predicates.add( qb.and( qb.equal(adminPath,canAdmin)) );

        if( canSend != null )
            predicates.add( qb.and( qb.equal(sendPath,canSend)) );

        cq.where(
                qb.and(
                        predicates.toArray(new Predicate[0])
                )
        );
        Long count = em.createQuery(cq).getSingleResult();


        return count > 0;
    }

    private final String tokenKey = "856war98mq7qE9NADH";
    public String getToken(Map<String,Object> user) {
        try {
            // 5 minuts
            user.put("validTo", System.currentTimeMillis() + ( 1000 * 60 * 5) );
            String userJSON = StringUtils.toJSON(user);
            userJSON = StringUtils.encrypt(userJSON,tokenKey);
            this.httpResponse.setHeader("Authorization", "token " + userJSON);
            return userJSON;
        } catch( Exception ex ) {
            logger.error(ex);
            return null;
        }
    }

    public Map<String,Object> readUserFromToken() {
        String token = this.httpRequest.getHeader("Authorization");
        if( token == null || token.equals(""))
            throw new RuntimeException("No se recibio el Token");

        logger.info("Token:" + token);
        token = token.substring(6);

        try {
            token = StringUtils.decrypt(token,tokenKey);
            Map<String,Object> userData = (Map<String,Object>)StringUtils.JSONFromString(token);
            long validTo = (Long)userData.get("validTo");

            if( System.currentTimeMillis() > validTo )
                throw new RuntimeException("Token caduco");

            return userData;
        } catch( Exception ex ) {
            logger.error(ex);
            throw new RuntimeException("Token invalido");
        }

    }



}
