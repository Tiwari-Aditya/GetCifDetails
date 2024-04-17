package in.co.indusnet.java21poc.repository;

import in.co.indusnet.java21poc.model.TblErrorMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TblErrorMessagesRepository extends JpaRepository<TblErrorMessages,Long> {
    public TblErrorMessages findByErrorCode(String errorCode);

    @Modifying(clearAutomatically = true)
    @Query(value = "ALTER TABLE tbl_error_messages AUTO_INCREMENT = 1", nativeQuery = true)
    void truncateTable();
}
