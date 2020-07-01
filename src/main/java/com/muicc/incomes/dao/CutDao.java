package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Cut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CutDao extends JpaRepository<Cut,Integer> {

    @Query(value = "select * from cut where ccid = ?1 and eid =?2 order by id asc",nativeQuery = true)
    List<Cut> getCutByEidAndCcid(int ccid, int eid);
}
