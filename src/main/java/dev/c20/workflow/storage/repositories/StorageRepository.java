package dev.c20.workflow.storage.repositories;

import dev.c20.workflow.storage.entities.Storage;
import dev.c20.workflow.storage.entities.adds.Perm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {

    @Query( "select o from Storage o where o.path = ?1 ")
    Storage getStorage(String path );

    @Query( "select o from Storage o where o.path = ?1 and o.isFolder = false")
    Storage getFile(String path );

    @Query( "select o from Storage o where o.path = ?1 and o.isFolder = true")
    Storage getFolder(String path );

    @Query( "select o from Storage o where o.path like ?1 and o.level = ?2 order by o.isFolder desc, o.name")
    List<Storage> dir(String path, Integer level );

    @Query( "select p from Perm p, Storage o where p.parent = o and o.path = ?1 and o.isFolder = true and p.user in ( ?2 ) and o.visible = true and o.deleted = false")
    List<Perm> getFolderPerms( String path, List<String> roles);

    @Query( "select s " +
            "  from Storage s " +
            " where s.path = ?1 " +
            "   and s.isFolder = true " +
            "   and s.visible = true " +
            "   and s.deleted = false  " +
            "   and ( s.restrictedByPerm = false " +
            "   or exists ( " +
            "      select p " +
            "        from Perm p " +
            "       where p.parent = s  " +
            "         and p.user in ( ?2 ) " +
            "         and ( p.canAdmin = true or p.canCreate = true )" +
            "   ) " +
            " )"
    )
    Storage  userCanCreateInFolder( String path, List<String> roles);

    @Query( "select s " +
            "  from Storage s " +
            " where s.path = ?1 " +
            "   and s.isFolder = true " +
            "   and s.visible = true " +
            "   and s.deleted = false  " +
            "   and ( s.restrictedByPerm = false " +
            "   or exists ( " +
            "      select p " +
            "        from Perm p " +
            "       where p.parent = s  " +
            "         and p.user in ( ?2 ) " +
            "         and ( p.canAdmin = true or p.canRead = true )" +
            "   ) " +
            " )"
    )
    Storage  userCanReadInFolder( String path, List<String> roles);

    @Query( "select s " +
            "  from Storage s " +
            " where s.path = ?1 " +
            "   and s.isFolder = true " +
            "   and s.visible = true " +
            "   and s.deleted = false  " +
            "   and ( s.restrictedByPerm = false " +
            "   or exists ( " +
            "      select p " +
            "        from Perm p " +
            "       where p.parent = s  " +
            "         and p.user in ( ?2 ) " +
            "         and ( p.canAdmin = true or p.canUpdate = true )" +
            "   ) " +
            " )"
    )
    Storage  userCanUpdateInFolder( String path, List<String> roles);

    @Query( "select s " +
            "  from Storage s " +
            " where s.path = ?1 " +
            "   and s.isFolder = true " +
            "   and s.visible = true " +
            "   and s.deleted = false  " +
            "   and ( s.restrictedByPerm = false " +
            "   or exists ( " +
            "      select p " +
            "        from Perm p " +
            "       where p.parent = s  " +
            "         and p.user in ( ?2 ) " +
            "         and ( p.canAdmin = true or p.canDelete = true )" +
            "   ) " +
            " )"
    )
    Storage  userCanDeleteInFolder( String path, List<String> roles);


    @Query( "select s " +
            "  from Storage s " +
            " where s.path = ?1 " +
            "   and s.isFolder = true " +
            "   and s.visible = true " +
            "   and s.deleted = false  " +
            "   and ( s.restrictedByPerm = false " +
            "   or exists ( " +
            "      select p " +
            "        from Perm p " +
            "       where p.parent = s  " +
            "         and p.user in ( ?2 ) " +
            "         and ( p.canAdmin = true )" +
            "   ) " +
            " )"
    )
    Storage  userCanAdminInFolder( String path, List<String> roles);


    @Query( "select s " +
            "  from Storage s " +
            " where s.path = ?1 " +
            "   and s.isFolder = true " +
            "   and s.visible = true " +
            "   and s.deleted = false  " +
            "   and ( s.restrictedByPerm = false " +
            "   or exists ( " +
            "      select p " +
            "        from Perm p " +
            "       where p.parent = s  " +
            "         and p.user in ( ?2 ) " +
            "         and ( p.canAdmin = true or p.canSend = true )" +
            "   ) " +
            " )"
    )
    Storage userCanSendInFolder( String path, List<String> roles);

}
