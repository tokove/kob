package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String,String> map = new HashMap<>();

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        if(title == null || title.isEmpty()){
            map.put("error_message", "名称不能为空");
            return map;
        }

        if(title.length() > 100) {
            map.put("error_message", "名称长度不能超过100");
            return map;
        }

        if(description == null || description.isEmpty()){
            description = "这个用户很懒，什么也没留下~";
        }

        if(description.length() > 300) {
            map.put("error_message", "描述长度不能超过300");
            return map;
        }

        if(content == null || content.isEmpty()){
            map.put("error_message", "代码不能为空");
            return map;
        }

        if(content.length() > 10000) {
            map.put("error_message", "代码长度不能超过10000");
            return map;
        }

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
            map.put("error_message", "没有权限修改该Bot");
            return map;
        }

        Bot newBot = new Bot(bot.getId(), user.getId(), title, description, content, bot.getRating(), bot.getCreateTime(), new Date());

        botMapper.updateById(newBot);
        map.put("error_message", "success");

        return map;
    }
}
