package ui;

import bean.DocBean;
import controller.DownloadController;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class Task extends Thread {

    final String getDocUrl = "https://live.azavt.com/chatroom/file/download?fileId=";

    final String getDocListUrl = "https://live.azavt.com/chatroom/file/list?chatRoomId=";

    public JProgressBar progressBar;

    int progress;

    public Map<String, List<DocBean>> docBeanMap;

    public List<DocBean> docBeanList;

    public String type;

    public String url;

    public String name;

    public Task() {
    }

    public Task(JProgressBar progressBar, int progress, Map<String, List<DocBean>> docBeanMap, List<DocBean> docBeanList, String type, String url, String name) {
        this.progressBar = progressBar;
        this.progress = progress;
        this.docBeanMap = docBeanMap;
        this.docBeanList = docBeanList;
        this.type = type;
        this.url = url;
        this.name = name;
    }

    public void run() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                float totalSize = docBeanList.size();
                float start = 1;
                if ("1".equals(type)) {
                    docBeanMap.forEach((key, value) -> {
                        runDownFile(totalSize, start, value, key);
                    });
                }else {
                    runDownFile(totalSize, start, docBeanList, name);
                }
            }
        });

    }

    private void runDownFile(float totalSize, float start, List<DocBean> docBeanList, String name) {
        for (DocBean docBean : docBeanList) {
            String docUrl = getDocUrl + docBean.getId();
            DownloadController.downloadFile(docUrl, name, docBean.getName());
            float progress = start/totalSize*100;
            int i = (int) progress;
            progressBar.setValue(i);
            start++;
        }
        JOptionPane.showMessageDialog(null, "下载完成");
    }
}
