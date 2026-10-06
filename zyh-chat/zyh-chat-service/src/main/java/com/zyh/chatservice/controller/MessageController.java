package com.zyh.chatservice.controller;

import com.zyh.chatservice.domain.dto.MessageListReqDTO;
import com.zyh.chatservice.domain.dto.MessageVisitedReqDTO;
import com.zyh.chatservice.domain.vo.MessageVO;
import com.zyh.chatservice.service.IMessageService;
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
@RequestMapping("/message")
public class MessageController {

    @Resource(name = "messageServiceImpl")
    private IMessageService messageService;

    /**
     * 获取历史聊天记录
     *
     * @param messageListReqDTO
     * @return
     */
    @PostMapping("/list")
    public R<List<MessageVO>> list(@Validated @RequestBody MessageListReqDTO messageListReqDTO) {
        return R.ok(messageService.list(messageListReqDTO));
    }

    /**
     * 更新消息访问状态
     *
     * @param messageVisitedReqDTO
     * @return
     */
    @PostMapping("/batch_visited")
    public R<?> batchVisited(@Validated @RequestBody MessageVisitedReqDTO messageVisitedReqDTO) {
        messageService.batchVisited(messageVisitedReqDTO);
        return R.ok();
    }
}
