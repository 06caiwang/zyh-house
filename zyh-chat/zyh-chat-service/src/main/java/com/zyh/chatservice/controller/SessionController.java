package com.zyh.chatservice.controller;

import com.zyh.chatservice.domain.dto.SessionAddReqDTO;
import com.zyh.chatservice.domain.dto.SessionGetReqDTO;
import com.zyh.chatservice.domain.dto.SessionListReqDTO;
import com.zyh.chatservice.domain.vo.SessionAddResVO;
import com.zyh.chatservice.domain.vo.SessionGetResVO;
import com.zyh.chatservice.service.ISessionService;
import com.zyh.commondomain.domain.R;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangyuheng
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    @Resource(name = "sessionServiceImpl")
    private ISessionService sessionService;

    /**
     * 新建咨询会话
     */
    @PostMapping("/add")
    public R<SessionAddResVO> add(@Validated @RequestBody SessionAddReqDTO sessionAddReqDTO) {
        return R.ok(sessionService.add(sessionAddReqDTO));
    }

    /**
     * 查询咨询会话
     */
    @PostMapping("/get")
    public R<SessionGetResVO> get(@Validated @RequestBody SessionGetReqDTO sessionGetReqDTO ) {
        return R.ok(sessionService.get(sessionGetReqDTO));
    }

    /**
     * 查询咨询会话列表
     */
    @PostMapping("/list")
    public R<List<SessionGetResVO>> list(@Validated @RequestBody SessionListReqDTO sessionListReqDTO ) {
        return R.ok(sessionService.list(sessionListReqDTO));
    }
}
