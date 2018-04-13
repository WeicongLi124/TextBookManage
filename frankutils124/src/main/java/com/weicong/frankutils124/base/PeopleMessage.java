package com.weicong.frankutils124.base;

/**
 * @author: Frank
 * @time: 2018/3/13 13:55
 * @e-mail: 912220261@qq.com
 * Function:
 */

public class PeopleMessage {
    private final String name;
    private final String sex;
    private final String phone;
    private final String birthday;
    private final String address;

    public PeopleMessage(String name, String sex, String phone, String birthday, String address) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public static class Builder{
        private String name;
        private String sex;
        private String phone;
        private String birthday;
        private String address;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSex(String sex) {
            this.sex = sex;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public PeopleMessage build(){
            initDefaultValue(this);
            return new PeopleMessage(name,sex,phone,birthday,address);
        }

        private void initDefaultValue(Builder builder){
            if (builder.name == null || builder.name.length()<1){
                builder.name = "李某某";
            }
            if (builder.sex == null || builder.sex.length()<1){
                builder.sex = "未知";
            }
            if (builder.phone == null || builder.phone.length()<1){
                builder.phone = "1303000000";
            }
            if (builder.birthday == null || builder.birthday.length()<1){
                builder.birthday = "1900-01-01";
            }
            if (builder.address == null || builder.address.length()<1){
                builder.address = "华南理工大学广州学院";
            }
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", phone='" + phone + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}
