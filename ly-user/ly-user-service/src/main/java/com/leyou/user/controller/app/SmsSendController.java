package com.leyou.user.controller.app;

import com.aliyuncs.CommonResponse;
import com.leyou.common.vo.Result;
import com.leyou.user.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
public class SmsSendController {

    @Autowired
    SmsSendService smsSendService;

    @GetMapping("captcha")
    public ResponseEntity<Result<Boolean>> sendSms(
            @RequestParam(value = "phone", required = true) String phone
    ) {
        Boolean sendSuccess = smsSendService.sendSms(phone);
        if (sendSuccess == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(Result.success(sendSuccess));
    }

}
