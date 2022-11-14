package com.litian.dancechar.idgenerator.core.uid.worker.inf;

import com.litian.dancechar.idgenerator.core.uid.worker.entity.WorkerNodeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WorkerNodeDao {

    WorkerNodeDO getWorkerNodeByHostPort(@Param("host") String host, @Param("port") String port);

    void addWorkerNode(WorkerNodeDO workerNodeEntity);
}
