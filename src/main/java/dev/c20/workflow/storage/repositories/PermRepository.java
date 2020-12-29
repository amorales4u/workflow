package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Perm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PermRepository extends JpaRepository<Perm, Long> {

    @Query( "select o from Perm o where o.parent = ?1 order by o.user")
    public List<Perm> getAll(Storage parent);

    @Query( "select o from Perm o where o.parent = ?1 and o.user = ?2 ")
    public Perm getByUser(Storage parent, String user);

    @Modifying
    @Query( "insert into Perm ( parent, user, canCreate, canUpdate, canDelete, canAdmin, canSend ) select ?1, user, canCreate, canUpdate, canDelete, canAdmin, canSend From Perm o where o.parent = ?2")
    @Transactional
    public int copyTo(Storage target, Storage source);

    @Transactional
    @Modifying
    @Query("delete from Perm o where o.parent = ?1")
    public int deleteFromParent( Storage storage);

    @Query( "select count(o) from Perm o where o.parent.id = ?1 and o.user in ( ?2 ) and ( o.canAdmin = true or o.canCreate = true  )")
    public int userHasCreatePermissionsInStorage( Long id, List<String> identities);

    @Query( "select count(o) from Perm o where o.parent.id = ?1 and o.user in ( ?2 ) and ( o.canAdmin = true  or o.canRead = true  )")
    public int userHasReadPermissionsInStorage( Long id, List<String> identities);

    @Query( "select count(o) from Perm o where o.parent.id = ?1 and o.user in ( ?2 ) and ( o.canAdmin = true  or o.canUpdate = true  ) ")
    public int userHasUpdatePermissionsInStorage( Long id, List<String> identities);

    @Query( "select count(o) from Perm o where o.parent.id = ?1 and o.user in ( ?2 ) and ( o.canAdmin = true  or o.canDelete = true  ) ")
    public int userHasDeletePermissionsInStorage( Long id, List<String> identities);

    @Query( "select count(o) from Perm o where o.parent.id = ?1 and o.user in ( ?2 ) and o.canAdmin = true ")
    public int userHasAdminPermissionsInStorage( Long id, List<String> identities);

    @Query( "select count(o) from Perm o where o.parent.id = ?1 and o.user in ( ?2 ) and ( o.canAdmin = true  or o.canSend = true  )")
    public int userHasSendPermissionsInStorage( Long id, List<String> identities);

    @Query( "select s from Perm o, Storage s where o.parent = s and s.path like '/Workflows/%' and s.level = 1 and o.user in ( ?1 ) and ( o.canAdmin = true  or o.canRead = true or o.canSend = true  ) order by s.path")
    public List<String> getWorkflows( List<String> identities);

    @Query( "select s.path from Perm o, Storage s where o.parent = s and s.path like ?1  and s.level = 2 and o.user in ( ?2 ) and ( o.canAdmin = true  or o.canRead = true or o.canSend = true  ) order by s.path")
    public List<String> getWorkflows( String workflow, List<String> identities);

    @Query( "select count(s.id) from Perm o, Storage s where o.parent = s and s.path = ?1  and o.user in ( ?2 ) and ( o.canAdmin = true  or o.canRead = true or o.canSend = true  ) order by s.path")
    public Long getWorkflowsCount( String workflow, List<String> identities);



}
