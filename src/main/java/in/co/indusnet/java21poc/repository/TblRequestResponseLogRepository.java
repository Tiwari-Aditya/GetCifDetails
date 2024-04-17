package in.co.indusnet.java21poc.repository;

import in.co.indusnet.java21poc.model.TblRequestResponseLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TblRequestResponseLogRepository extends JpaRepository<TblRequestResponseLog, Long> {

    public List<TblRequestResponseLog> findById(long id);

    public List<TblRequestResponseLog> findByReqIdAndActivityTypeOrderByIdDesc(long reqId, String activityType);

    public List<TblRequestResponseLog> findByReqIdAndIblApiReqId(long reqId, String iblApiReqId);


}