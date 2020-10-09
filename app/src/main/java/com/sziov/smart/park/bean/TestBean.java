package com.sziov.smart.park.bean;

import java.util.List;

/**
 * @ProjectName: SmartPark
 * @Package: com.sziov.smart.park.bean
 * @Author: willkong
 * @CreateDate: 2020/9/29 18:07
 * @Description: java类作用描述
 */
public class TestBean {

    /**
     * code : 0
     * message : 调用成功
     * data : [{"type":0,"parkId":1,"cameraDtoList":[{"id":35,"status":1}]},{"type":0,"parkId":2,"cameraDtoList":[{"id":33,"status":1},{"id":34,"status":1}]},{"type":1,"parkId":null,"cameraDtoList":[]}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : 0
         * parkId : 1
         * cameraDtoList : [{"id":35,"status":1}]
         */

        private int type;
        private int parkId;
        private List<CameraDtoListBean> cameraDtoList;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getParkId() {
            return parkId;
        }

        public void setParkId(int parkId) {
            this.parkId = parkId;
        }

        public List<CameraDtoListBean> getCameraDtoList() {
            return cameraDtoList;
        }

        public void setCameraDtoList(List<CameraDtoListBean> cameraDtoList) {
            this.cameraDtoList = cameraDtoList;
        }

        public static class CameraDtoListBean {
            /**
             * id : 35
             * status : 1
             */

            private int id;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
