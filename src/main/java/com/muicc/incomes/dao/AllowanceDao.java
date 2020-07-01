package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AllowanceDao extends JpaRepository<Allowance,Integer> {
    @Query(value = "select * from allowance where alcid = ?1 and eid =?2 order by id asc",nativeQuery = true)
    List<Allowance> getAllowanceByEidAndAlcid(int alcid,int eid);
}
