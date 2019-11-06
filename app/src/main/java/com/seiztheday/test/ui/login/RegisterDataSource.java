package com.seiztheday.test.ui.login;

import com.seiztheday.test.data.Result;
import com.seiztheday.test.data.model.LoggedInUser;

import java.io.IOException;

class RegisterDataSource
{
    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "你好");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
