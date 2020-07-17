package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployerDao extends JpaRepository<Employer,Integer> {

    @Query(value = "select * from employer where id = ?1 and name = ?2 and status = 1",nativeQuery = true)
    Employer getEmployerByIdAndName(int id, String name);

    @Query(value = "select * from employer where id = ?1",nativeQuery = true)
    Employer getEmployerById(int id);

    @Query(value = "select * from employer where name = ?1",nativeQuery = true)
    Employer getEmployerByName(String name);

    @Query(value = "select * from employer where status = 1 order by id asc",nativeQuery = true)
    List<Employer> getAllEmployer();

    @Transactional
    @Modifying
    @Query(value = "insert into employer(id,name,status,password) values (?1,?2,1,null) ",nativeQuery = true)
    int addEmployer(int id,String name);

    @Transactional
    @Modifying
    @Query(value = "update employer set name =?2 where id = ?1 ",nativeQuery = true)
    int updateEmployer(int id,String name);

    @Transactional
    @Modifying
    @Query(value = "delete from employer where id = ?1",nativeQuery = true)
    int deleteEmployer(int id);
}
