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
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户获取otp短信接口
    @RequestMapping("/getotp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone")String telphone){
        //需要按照一定的规制生成OTP验证码
        Random random=new Random();
        int randomInt=random.nextInt(99999);
        randomInt+=10000;
        String otpCode=String.valueOf(randomInt);
        //将OTP验证码同对应用户的手机号关联(使用HttpSession的方式绑定他的手机号与OTPCODE)
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //将OTP验证码通过短信通道发送给用户，（先不做）
        System.out.println("telphone: "+telphone+" &otpCode: "+otpCode);

        return CommonReturnType.create(null);
    }

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
