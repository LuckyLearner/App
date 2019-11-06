package com.seiztheday.test.ui.login;

import androidx.annotation.Nullable;

public class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer real_nameError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,@Nullable Integer real_nameError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.real_nameError=real_nameError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.real_nameError=null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }
    @Nullable
    Integer getReal_nameError(){return  real_nameError;}

    boolean isDataValid() {
        return isDataValid;
    }
}
