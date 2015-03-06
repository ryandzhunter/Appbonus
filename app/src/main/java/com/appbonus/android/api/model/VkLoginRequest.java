package com.appbonus.android.api.model;

public class VkLoginRequest {
    protected User user;

    public VkLoginRequest() {
        user = new User();
    }

    public VkLoginRequest(String vkToken, String email, String phone) {
        this(vkToken);
        user.setEmail(email);
        user.setPhone(phone);
    }

    public VkLoginRequest(String vkToken) {
        this();
        user.setVkToken(vkToken);
    }

    static class User {
        protected String vkToken;
        protected String email;
        protected String phone;

        public void setVkToken(String vkToken) {
            this.vkToken = vkToken;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
