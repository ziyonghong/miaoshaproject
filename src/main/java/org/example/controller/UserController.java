package org.example.controller;

import com.sun.xml.internal.rngom.parse.host.Base;
import org.example.error.BusinessException;
import org.example.error.EmBusinessError;
import org.example.response.CommonReturnType;
import org.example.service.UserService;
import org.example.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel=userService.getUserById(id);
     //若获取的对应用户信息不存在
        if(userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        return CommonReturnType.create(userModel);
    }

//    //定义exceptionhandler解决未被controller层吸收的exception
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public Object handlerException(HttpServletRequest request,Exception ex){
//        Map<String,Object> responseData=new HashMap<>();
//        if(ex instanceof BusinessException){
//            BusinessException businessException= (BusinessException)ex;
//            responseData.put("errCode",businessException.getErrCode());
//            responseData.put("errMsg",businessException.getErrMsg());
//        }else{
//            responseData.put("errCode",EmBusinessError.UNKNOWN_ERROR.getErrCode());
//            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrMsg());
//        }
//
//        return CommonReturnType.create(responseData,"fail");

//        BusinessException businessException= (BusinessException)ex;
////        CommonReturnType commonReturnType=new CommonReturnType();
////        commonReturnType.setStatus("fail");
//        Map<String,Object> responseData=new HashMap<>();
//        responseData.put("errCode",businessException.getErrCode());
//        responseData.put("errMsg",businessException.getErrMsg());
////        commonReturnType.setData(responseData);
////        return commonReturnType;
//        return CommonReturnType.create(responseData,"fail");
//    }

}
