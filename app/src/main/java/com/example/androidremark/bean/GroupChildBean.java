package com.example.androidremark.bean;

import java.util.List;

/**
 * Created by caobin on 2017/9/14.
 */

public class GroupChildBean {

    private ResultMsgBean resultMsg;

    public ResultMsgBean getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(ResultMsgBean resultMsg) {
        this.resultMsg = resultMsg;
    }

    public static class ResultMsgBean {
        private List<GroupListBean> groupList;

        public List<GroupListBean> getGroupList() {
            return groupList;
        }

        public void setGroupList(List<GroupListBean> groupList) {
            this.groupList = groupList;
        }

        public static class GroupListBean {

            private String groupName;
            private List<ChildListBean> childList;

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public List<ChildListBean> getChildList() {
                return childList;
            }

            public void setChildList(List<ChildListBean> childList) {
                this.childList = childList;
            }

            public static class ChildListBean {

                private String childName;
                private String openTime;
                private boolean isGroup;

                /**
                 * 添加child
                 *
                 * @param childName
                 */
                public ChildListBean(String childName) {
                    this.childName = childName;
                }

                /**
                 * 把group当成child
                 *
                 * @param childName
                 * @param isGroup
                 */
                public ChildListBean(String childName, boolean isGroup) {
                    this.childName = childName;
                    this.isGroup = isGroup;
                }

                public boolean isGroup() {
                    return isGroup;
                }

                public void setGroup(boolean group) {
                    isGroup = group;
                }

                public String getChildName() {
                    return childName;
                }

                public void setChildName(String childName) {
                    this.childName = childName;
                }

                public String getOpenTime() {
                    return openTime;
                }

                public void setOpenTime(String openTime) {
                    this.openTime = openTime;
                }
            }
        }
    }
}
