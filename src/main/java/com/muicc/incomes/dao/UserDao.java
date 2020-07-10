package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {

    @Query(value = "select * from user where userobjectid = ?1 and status =1",nativeQuery = true)
    List<User> getUserById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into user(username,usercount,status,userobjectid) values (?1,?2,?3,?4) ",nativeQuery = true)
    int addUser(String username,double usercount,int status,int userobjectid);

    @Transactional
    @Modifying
    @Query(value = "delete from user where userobjectid = ?1",nativeQuery = true)
    int deleteUser(int userobjectId);

}
