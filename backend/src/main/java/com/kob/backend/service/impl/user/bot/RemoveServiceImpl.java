package com.kob.backend.service.impl.user.bot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class RemoveServiceImpl implements RemoveService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String,String> map = new HashMap<>();

        String botIdStr = data.get("bot_id");
        if(botIdStr == null || botIdStr.trim().isEmpty()) {
            map.put("error_message", "bot_id不能为空");
            return map;
        }

        int botId = Integer.parseInt(botIdStr);
        Bot bot = botMapper.selectById(botId);

        if(bot == null){
            map.put("error_message", "Bot不存在或已被删除");
            return map;
        }

        if(!Objects.equals(bot.getUserId(), user.getId())){
            map.put("error_message", "没有权限删除该Bot");
            return map;
        }

        botMapper.deleteById(botId);
        map.put("error_message", "success");

        return map;
    }
}
