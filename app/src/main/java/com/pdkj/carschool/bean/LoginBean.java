package com.pdkj.carschool.bean;

public class LoginBean {
    private String message;
    private String code;
    private UserMessage data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserMessage getData() {
        return data;
    }

    public void setData(UserMessage data) {
        this.data = data;
    }


    public class TokenModel{
        private int userId;
        private String token;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public class UserMessage{
        private TokenModel token;
        private InformationBean user;


        public TokenModel getToken() {
            return token;
        }

        public void setToken(TokenModel token) {
            this.token = token;
        }

        public InformationBean getInformationBean() {
            return user;
        }

        public void setInformationBean(InformationBean informationBean) {
            this.user = informationBean;
        }
    }
}
