package com.muicc.incomes.controller;


import com.muicc.incomes.dao.CreatedateDao;
import com.muicc.incomes.pojo.Createdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CreatedateController {

    @Autowired
    CreatedateDao createdateDao;

    @CrossOrigin
    @GetMapping(value = "incomes/getAllCreatedate")
    @ResponseBody
    public List getAllCreatedate() {

        List<Createdate> createdateList = createdateDao.getAllCreatedate();

        return createdateList;
    }
}
