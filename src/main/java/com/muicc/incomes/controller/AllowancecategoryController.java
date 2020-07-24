package com.muicc.incomes.controller;


import com.muicc.incomes.dao.AllowancecategoryDao;
import com.muicc.incomes.pojo.Allowancecategory;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AllowancecategoryController {

    @Autowired
    AllowancecategoryDao allowancecategoryDao;


    //查找所有补贴种类信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllAllowancecategory")
    @ResponseBody
    public List getAllAllowancecategory() {

        List<Allowancecategory> allowancecategoryList = allowancecategoryDao.getAllAllowancecategory();
        List list = new ArrayList<>();
        for(int i = 0; i < allowancecategoryList.size(); i++){
            Map map = new HashMap();
            Allowancecategory allowancecategory = allowancecategoryList.get(i);
            map.put("num", i+1);//序号
            map.put("id",allowancecategory.getId());//ID
            map.put("name", allowancecategory.getName());//补贴种类
            String shown = null;
            if(allowancecategory.getShown()==0){
                shown = "否";
            }else{
                shown = "是";
            }
            map.put("shown", shown);//是否显示在工资表

            list.add(map);
        }
        return list;
    }

    //添加补贴种类信息
    @CrossOrigin
    @PostMapping("incomes/addAllowancecategory")
    @ResponseBody
    @Transactional
    public Result addAllowancecategory(@RequestBody Allowancecategory requestAllowancecategory) {
        String name =requestAllowancecategory.getName();//补贴名称
        int shown =requestAllowancecategory.getShown();//是否显示在工资表
        String message = String.format("添加成功！");
        if (null == name||name.equals("")) {
            message = String.format("添加补贴种类名称不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int i = allowancecategoryDao.addAllowancecategory(name,shown);
        if(i==0){
            message = String.format("添加失败！错误代码440");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //按ID修改补贴种类信息
    @CrossOrigin
    @PostMapping("incomes/updateAllowancecategory")
    @ResponseBody
    @Transactional
    public Result updateAllowancecategory(@RequestBody Allowancecategory requestAllowancecategory) {
        int id =requestAllowancecategory.getId();//ID
        String name =requestAllowancecategory.getName();//补贴名称
        int shown =requestAllowancecategory.getShown();//是否显示在工资表
        String message = String.format("修改成功！");
        if (null == name ||name.equals("")) {
            message = String.format("修改补贴种类名称不能为空");
            return ResultFactory.buildFailResult(message);
        }
        int i = allowancecategoryDao.updateAllowancecategory(name,shown,id);
        if(i==0){
            message = String.format("修改失败！错误代码440");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //按ID删除补贴种类信息（假删除）
    @CrossOrigin
    @PostMapping("incomes/deleteAllowancecategory")
    @ResponseBody
    @Transactional
    public Result deleteAllowancecategory(@RequestBody Allowancecategory requestAllowancecategory) {
        int id =requestAllowancecategory.getId();//ID
        String message = String.format("删除成功！");
        if (0 == id) {
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = allowancecategoryDao.deleteAllowancecategory(id);
        if(i==0){
            message = String.format("删除失败！错误代码440");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }
}
