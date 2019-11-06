package com.seiztheday.test.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.seiztheday.test.R;

public class RegisterViewModel extends ViewModel
{
        MutableLiveData<RegisterFormState>registerFormState=new MutableLiveData<>();

    public RegisterViewModel(RegisterRepository instance) {

    }

    LiveData<RegisterFormState> getRegisterFormState()
    {
        return registerFormState;
    }


    public void loginDataChanged(String username, String password,String real_name) {
        if (!isUserNameValid(username)) {
            registerFormState.postValue(new RegisterFormState(R.string.invalid_username, null,null));
        } else if (!isPasswordValid(password)) {
            registerFormState.postValue(new RegisterFormState(null, R.string.invalid_register_password,null));
        }
        else if (!isReal_NameValid(real_name)){
            registerFormState.postValue((new RegisterFormState(null,null,R.string.invalid_real_name)));

        }
            else {
            registerFormState.postValue(new RegisterFormState(true));
        }
    }


    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
    private boolean isReal_NameValid(String real_name){

  return  !real_name.replace(" ","").isEmpty();

}


    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 1;
    }

}
