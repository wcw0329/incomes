package com.muicc.incomes.controller;


import com.muicc.incomes.dao.AwardcategoryDao;
import com.muicc.incomes.pojo.Awardcategory;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AwardcategoryController {

    @Autowired
    AwardcategoryDao awardcategoryDao;


    //查找所有奖金种类信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllAwardcategory")
    @ResponseBody
    public List getAllAwardcategory() {

        List<Awardcategory> awardcategoryList = awardcategoryDao.getAllAwardcategory();
        List list = new ArrayList<>();
        for(int i = 0; i < awardcategoryList.size(); i++){
            Map map = new HashMap();
            Awardcategory awardcategory = awardcategoryList.get(i);
            map.put("num", i+1);//序号
            map.put("id",awardcategory.getId());//ID
            map.put("name", awardcategory.getName());//奖金种类
            String shown = null;
            if(awardcategory.getShown()==0){
                shown = "否";
            }else{
                shown = "是";
            }
            map.put("shown", shown);//是否显示在工资表

            list.add(map);
        }
        return list;
    }

    //添加奖金种类信息
    @CrossOrigin
    @PostMapping("incomes/addAwardcategory")
    @ResponseBody
    public Result addAwardcategory(@RequestBody Awardcategory requestAwardcategory) {
        String name =requestAwardcategory.getName();//奖金名称
        int shown =requestAwardcategory.getShown();//是否显示在工资表
        String message = String.format("添加成功！");
        if (null == name) {
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = awardcategoryDao.addAwardcategory(name,shown);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按ID修改奖金种类信息
    @CrossOrigin
    @PostMapping("incomes/updateAwardcategory")
    @ResponseBody
    public Result updateAwardcategory(@RequestBody Awardcategory requestAwardcategory) {
        int id =requestAwardcategory.getId();//ID
        String name =requestAwardcategory.getName();//奖金名称
        int shown =requestAwardcategory.getShown();//是否显示在工资表
        String message = String.format("修改成功！");
        if (null == name || 0 == id ) {
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = awardcategoryDao.updateAwardcategory(name,shown,id);
        if(i==0){
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按ID删除奖金种类信息（假删除）
    @CrossOrigin
    @PostMapping("incomes/deleteAwardcategory")
    @ResponseBody
    public Result deleteAwardcategory(@RequestBody Awardcategory requestAwardcategory) {
        int id =requestAwardcategory.getId();//ID
        String message = String.format("删除成功！");
        if (0 == id) {
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = awardcategoryDao.deleteAwardcategory(id);
        if(i==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

}
