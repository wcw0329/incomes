package com.muicc.incomes.controller;


import com.muicc.incomes.dao.UserDao;
import com.muicc.incomes.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    //查找员工的工资管理明细
    @CrossOrigin
    @GetMapping(value = "incomes/getUserById")
    @ResponseBody
    public List getUserById(int id) {
        List<User> userById = userDao.getUserById(id);
        List list = new ArrayList<>();
        for (int i = 0; i < userById.size(); i++) {
            User user = userById.get(i);
            Map map = new HashMap();
            map.put("num", i+1);//序号
            map.put("detail", user.getUsername());//详情
            map.put("amount", user.getUsercount());//金额
            list.add(map);
        }
            return list;
    }

}
