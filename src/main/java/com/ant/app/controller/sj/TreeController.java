package com.ant.app.controller.sj;

import com.ant.app.Constants;
import com.ant.app.entity.AppWebResult;
import com.ant.app.entity.tree.TreNode;
import com.ant.app.service.TreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author lchunlei
 * @date 2018/12/25
 */
@RestController
@RequestMapping("/sj/tree")
public class TreeController {

    private static final Logger log = LoggerFactory.getLogger(TreeController.class);
    @Autowired
    TreeService treeService;

    /**
     * 初始化树图
     * data携带用户ID，并保存到session
     */
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public TreNode treeInit(HttpSession session,Integer nodeUserId){
        log.info("加载树节点---》"+nodeUserId);
        AppWebResult<TreNode> result = new AppWebResult();
        if(session.getAttribute(Constants.USER_ID)==null){
            return null;
        }else {
            if(nodeUserId==null||nodeUserId.equals(0)){
                nodeUserId=(int)session.getAttribute(Constants.USER_ID);
            }
            treeService.initTree(nodeUserId,result);
            if(Constants.SUCCESS_CODE.equals(result.getResultCode())){
                return result.getData();
            }else {
                return null;
            }
        }
    }

    /**
     * 输入密码节点查看树
     */
    @RequestMapping(value = "/look",method = RequestMethod.GET)
    public AppWebResult treeLook(HttpSession session,String secondPwd){
        log.info("输入密码节点查看树req---"+secondPwd);
        AppWebResult result = new AppWebResult();
        Object obj = session.getAttribute(Constants.USER_ID);
        if(obj==null){
            result.setResultCode(Constants.NO_LOGIN_CODE);
            result.setResultMsg(Constants.NO_LOGIN);
        }else {
            int userId = (int)obj;
            treeService.preFindTree(userId,secondPwd,result);
        }
        log.info("输入密码节点查看树resp---"+result);
        return result;
    }


}
