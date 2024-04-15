package aiguquan.service;

import aiguquan.bean.ForumBean;
import aiguquan.bean.MessageBean;
import aiguquan.ui.MessageUI;
import bean.MainBean;
import com.alibaba.fastjson2.JSON;
import controller.DataController;

import java.util.LinkedList;

import static cache.DataCache.forumBeanMap;

public class MessageService {

    public void messageSave(MessageBean messageBean, String forumId){
        ForumBean forumBean = forumBeanMap.get(forumId);
        LinkedList<MessageBean> messageBeanList = forumBean.getMessageBeanList();
        //如果这个消息已经存在，则不添加
        if (messageBeanList.stream().anyMatch(messageBean1 -> messageBean1.getId() == messageBean.getId())){
            MessageBean oldMessage = messageBeanList.stream().filter(messageBean1 -> messageBean1.getId() == messageBean.getId()).findFirst().get();
            oldMessage.setSend(messageBean.isSend());
        }else {
            //如果不存在则添加
            messageBeanList.add(messageBean);
        }

        forumBean.setMessageBeanList(messageBeanList);
        forumBeanMap.put(forumId, forumBean);
        MainBean mainBean = DataController.mainBeanUpdate();
        // 保存数据到持久层
        DataController.saveDataToFile(JSON.toJSONString(mainBean));
        MessageUI.tableUpdate(messageBean.getId(),1,messageBean.isSend());
    }
}
