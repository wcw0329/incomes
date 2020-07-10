package com.muicc.incomes.controller;

import com.muicc.incomes.dao.CutcategoryDao;
import com.muicc.incomes.pojo.Cutcategory;
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
public class CutcategoryController {

    @Autowired
    CutcategoryDao cutcategoryDao;


    //查找所有扣款种类信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllCutcategory")
    @ResponseBody
    public List getAllCutcategory() {

        List<Cutcategory> cutcategoryList = cutcategoryDao.getAllCutcategory();
        List list = new ArrayList<>();
        for(int i = 0; i < cutcategoryList.size(); i++){
            Map map = new HashMap();
            Cutcategory cutcategory = cutcategoryList.get(i);
            map.put("num", i+1);//序号
            map.put("id",cutcategory.getId());//ID
            map.put("name", cutcategory.getName());//奖金种类
            String shown = null;
            if(cutcategory.getShown()==0){
                shown = "否";
            }else{
                shown = "是";
            }
            map.put("shown", shown);//是否显示在工资表

            list.add(map);
        }
        return list;
    }

    //添加扣款种类信息
    @CrossOrigin
    @PostMapping("incomes/addCutcategory")
    @ResponseBody
    public Result addCutcategory(@RequestBody Cutcategory requestCutcategory) {
        String name =requestCutcategory.getName();//扣款名称
        int shown =requestCutcategory.getShown();//是否显示在工资表
        String message = String.format("添加成功！");
        if (null == name) {
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = cutcategoryDao.addCutcategory(name,shown);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按ID修改扣款种类信息
    @CrossOrigin
    @PostMapping("incomes/updateCutcategory")
    @ResponseBody
    public Result updateCutcategory(@RequestBody Cutcategory requestCutcategory) {
        int id =requestCutcategory.getId();//ID
        String name =requestCutcategory.getName();//扣款名称
        int shown =requestCutcategory.getShown();//是否显示在工资表
        String message = String.format("修改成功！");
        if (null == name || 0 == id ) {
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = cutcategoryDao.updateCutcategory(name,shown,id);
        if(i==0){
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按ID删除扣款种类信息（假删除）
    @CrossOrigin
    @PostMapping("incomes/deleteCutcategory")
    @ResponseBody
    public Result deleteCutcategory(@RequestBody Cutcategory requestCutcategory) {
        int id =requestCutcategory.getId();//ID
        String message = String.format("删除成功！");
        if (0 == id) {
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = cutcategoryDao.deleteCutcategory(id);
        if(i==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }
}
