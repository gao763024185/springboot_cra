package com.gao.ssm.module.controller.user;

import com.gao.ssm.module.pojo.logs.Logs;
import com.gao.ssm.module.service.logs.LogsService;
import com.gao.ssm.module.service.user.UserInfoService;
import com.gao.ssm.module.tools.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 孙皓 on 2016/12/13.
 */
@Controller
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private LogsService logsService;

    private String view_base = "/p/";
    @RequestMapping("/crainnogao")
    public String showTest(ModelMap modelMap,@RequestParam(value = "pageNum",required = false) Integer pageNum){
        if (pageNum == null){
            pageNum=1;
        }
        int pageSize=5;
        List<Logs> logsList = logsService.findAll();
        Pager<Logs> pager = new Pager<Logs>(pageNum,pageSize,logsList);
        modelMap.addAttribute("logs",pager);
        modelMap.addAttribute("logsCount",pager.getDataList().size());
        return "crainnogao";
    }

//    @RequestMapping("fenye")
//    @ResponseBody
//    public Object fenye(@RequestParam(value = "pageNum",required = false) Integer pageNum,
//        @RequestParam(value = "pageSize",required = false) Integer pageSize){
//
//        pageSize=5;
//        List<Logs> logsList = logsService.findAll();
//        Pager<Logs> logs = new Pager<Logs>(pageNum,pageSize,logsList);
//        return new JsonResp(JsonResp.Result_Success,null,null,logs.getDataList());
//    }
//    @RequestMapping("/logshow")
//    public String logshow(ModelMap modelMap,@RequestParam(value = "logId") String logId){
//
//        Logs logs = logsService.getById(logId);
//        modelMap.addAttribute("log",logs);
//        return view_base+"logshow";
//    }
}
