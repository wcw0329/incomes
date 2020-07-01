package com.muicc.incomes.controller;


import com.muicc.incomes.dao.StayDao;
import com.muicc.incomes.pojo.Stay;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StayController {

    @Autowired
    StayDao stayDao;


    //查找所有的旷工扣款规则信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllStay")
    @ResponseBody
    public List getAllStay() {

        List<Stay> stayList = stayDao.getAllStay();
        List list = new ArrayList<>();
        for(int i = 0; i < stayList.size(); i++){
            Map map = new HashMap();
            Stay stay = stayList.get(i);
            map.put("num", i+1);//序号
            map.put("id", stay.getId());//ID
            map.put("updatedate",stay.getUpdatedate());//更新时间
            map.put("bei", stay.getBei());//奖励金额
            list.add(map);
        }
        return list;
    }

    //添加旷工扣款规则信息
    @CrossOrigin
    @PostMapping("incomes/addStay")
    @ResponseBody
    public Result addStay(@RequestBody Stay requestStay) throws ParseException {
        String message = String.format("添加成功！");
        Double bei = requestStay.getBei();//旷工扣款倍数
        String updatedate = requestStay.getUpdatedate();//更新时间

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int i = stayDao.addStay(bei,simpleDateFormat.parse(updatedate));
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按旷工扣款规则ID修改旷工扣款规则信息
    @CrossOrigin
    @PutMapping("incomes/updateStay")
    @ResponseBody
    public Result updateStay(@RequestBody Stay requestStay) throws ParseException {
        String message = String.format("修改成功！");
        Double bei = requestStay.getBei();//旷工扣款倍数
        String updatedate = requestStay.getUpdatedate();//更新时间
        int id = requestStay.getId();//ID
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int i = stayDao.updateStay(bei,simpleDateFormat.parse(updatedate),id);
        if(i==0){
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按ID删除旷工扣款规则信息
    @CrossOrigin
    @DeleteMapping("incomes/deleteStay")
    @ResponseBody
    public Result deleteStay(@RequestBody Stay requestStay) {
        String message = String.format("删除成功！");
        int id = requestStay.getId();//ID
        int i = stayDao.deleteStay(id);
        if(i==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }
}
