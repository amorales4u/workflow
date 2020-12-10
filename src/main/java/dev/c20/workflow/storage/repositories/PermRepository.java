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


}
