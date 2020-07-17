package com.muicc.incomes.controller;


import com.muicc.incomes.dao.CreatedateDao;
import com.muicc.incomes.dao.SumDao;
import com.muicc.incomes.dao.TempoDao;
import com.muicc.incomes.model.RequestTempo;
import com.muicc.incomes.pojo.Createdate;
import com.muicc.incomes.pojo.Sum;
import com.muicc.incomes.pojo.Tempo;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SumController {

    @Autowired
    SumDao sumDao;

    @Autowired
    CreatedateDao createdateDao;

    @Autowired
    TempoDao tempoDao;

    //查找所有的临时工资信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllSum")
    @ResponseBody
    public List getAllSum() {

        List<Sum> sumList = sumDao.getAllSum();
        List list = new ArrayList<>();
        for(int i = 0; i < sumList.size(); i++){
            Map map = new HashMap();
            Sum sum = sumList.get(i);
            map.put("num", i+1);//序号
            map.put("id", sum.getId());//ID
            map.put("time", createdateDao.getCreatedateByCdid(sum.getCdid()).getName());//时间
            map.put("sum",sum.getSum1());//合计
            list.add(map);
        }
        return list;
    }

    //添加临时工资信息
    @CrossOrigin
    @PostMapping("incomes/addSum")
    @ResponseBody
    public Result addSum(@RequestBody RequestTempo requestTempo) throws ParseException {
        String message = String.format("添加成功！");
        String name =requestTempo.getName();//员工姓名
        String time =requestTempo.getTime();//时间
        Double secondsum =requestTempo.getSecondsum();//工资合计
        Double tax =requestTempo.getTax();//代扣个税
        Double sumpayable =requestTempo.getSumpayable();//实发金额
        String note =requestTempo.getNote();//备注
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        int cdid = createdateByTime.getId();
        if(null==createdateByTime){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }
        //检测数据记录是否存在
        Tempo tempoByCdidAndName = tempoDao.getTempoByCdidAndName(cdid, name);
        if(tempoByCdidAndName!=null){
            message = String.format("添加失败！该员工本月已存在临时工资信息!");
            return ResultFactory.buidResult(100,message,tempoByCdidAndName);
        }
        //添加数据记录
        int i = tempoDao.addTempo(cdid, name, secondsum, tax, sumpayable, note);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else {
            Sum sumByCdid = sumDao.getSumByCdid(cdid);
            if (null == sumByCdid) {
                int j = sumDao.addSum(cdid, sumpayable);
                if (j == 0) {
                    message = String.format("添加失败！");
                    return ResultFactory.buildFailResult(message);
                } else {
                    return ResultFactory.buildSuccessResult(message, i);
                }
            } else {
                int k = sumDao.updateSum(cdid, sumpayable);
                if (k == 0) {
                    message = String.format("添加失败！");
                    return ResultFactory.buildFailResult(message);
                } else {
                    return ResultFactory.buildSuccessResult(message, i);
                }
            }
        }
    }

    //按ID删除临时工资信息
    @CrossOrigin
    @PostMapping("incomes/deleteSum")
    @ResponseBody
    public Result deleteSum(@RequestBody Sum requestStay){
        String message = String.format("删除成功！");
        int id = requestStay.getId();//ID
        Sum sumById = sumDao.getSumById(id);
        int i = sumDao.deleteSum(id);
        if(i==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            int j = tempoDao.deleteTempo(sumById.getCdid());
            if(j==0){
                message = String.format("删除失败！");
                return ResultFactory.buildFailResult(message);
            }
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

}
