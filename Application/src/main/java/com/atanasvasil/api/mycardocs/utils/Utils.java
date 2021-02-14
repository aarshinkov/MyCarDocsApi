package com.atanasvasil.api.mycardocs.utils;

import com.atanasvasil.api.mycardocs.entities.UserEntity;
import com.atanasvasil.api.mycardocs.responses.users.UserGetResponse;

public class Utils {

    public static UserGetResponse getUserFromEntity(UserEntity user) {
        UserGetResponse ugr = new UserGetResponse();
        ugr.setUserId(user.getUserId());
        ugr.setEmail(user.getEmail());
        ugr.setFirstName(user.getFirstName());
        ugr.setLastName(user.getLastName());
        ugr.setEditedOn(user.getEditedOn());

        return ugr;
    }
}
