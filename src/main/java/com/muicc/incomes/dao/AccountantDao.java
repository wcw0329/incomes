package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountantDao extends JpaRepository<Accountant,Integer>{

    @Query(value = "select * from accountant where id = ?1",nativeQuery = true)
    Accountant getAccountantById(int id);

    @Query(value = "select * from accountant where name = ?1 and password = ?2",nativeQuery = true)
    Accountant getAccountantByNameAndPassword(String name,String password);

    @Query(value = "select * from accountant ",nativeQuery = true)
    List<Accountant> getAllAccountant();

    @Transactional
    @Modifying
    @Query(value = "insert into accountant(name,password) values (?1,?2) ",nativeQuery = true)
    int addAccountant(String name,String password);

    @Transactional
    @Modifying
    @Query(value = "update accountant set name = ?1,password =?2 where id = ?3 ",nativeQuery = true)
    int updateAccountant(String name,String password,int id);

    @Transactional
    @Modifying
    @Query(value = "delete from accountant where id = ?1",nativeQuery = true)
    int deleteAccountant(int id);

}
