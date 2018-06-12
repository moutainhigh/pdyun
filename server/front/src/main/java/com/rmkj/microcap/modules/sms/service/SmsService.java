package com.rmkj.microcap.modules.sms.service;

import com.rmkj.microcap.common.bean.ResponseErrorEntity;
import com.rmkj.microcap.common.bean.StatusCode;
import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.constants.RegexpConstants;
import com.rmkj.microcap.common.modules.sms.service.SmsSendService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.common.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

/**
 * Created by renwp on 2016/10/18.
 */
@Service
@Transactional
public class SmsService {

    @Autowired
    private SmsSendService smsSendService;

    public ResponseEntity sendSmsValidateCode(String mobile, String type) {
        // reg|mtpwd|mmoble|moneyout
        String remark = null;
        switch (type){
            case "reg": remark = "注册账号"; break;
            case "mtpwd": remark = "修改交易密码"; break;
            case "mmoble": remark = "修改手机号"; break;
            case "moneyout": remark = "提现"; break;
            case "forgetPwd": remark = "忘记密码"; break;
        }
        // 校验手机号和类型
        if(!Pattern.matches(RegexpConstants.MOBILE, mobile) || remark == null){
            return new ResponseErrorEntity(StatusCode.VALIDATION_FAILED, HttpStatus.BAD_REQUEST);
        }
        String code = Utils.generateSmsCode();


        if(!smsSendService.send(Utils.formatStr(ProjectConstants.VALIDATE_CODE_MSG, code, remark), mobile)){
            return new ResponseErrorEntity(StatusCode.SEND_SMS_ERROR, HttpStatus.BAD_REQUEST);
        }
        ValidateCodeUtils.putMobileValidateCode(mobile, code, type);
        return new ResponseEntity(HttpStatus.OK);
    }

    public boolean sendSmsRemindUnitsAddMoney(String content, String mobile){
        //验证手机号和类型
        if(!Pattern.matches(RegexpConstants.MOBILE, mobile) || content == null){
            return false;
        }
        return smsSendService.send(content, mobile);
    }
}
