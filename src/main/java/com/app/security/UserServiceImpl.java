package com.app.security;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String login) {
        User user = new User();
        user.setLogin(login);
        //1234
        //https://www.devglan.com/online-tools/bcrypt-hash-generator
        user.setPassword("$2a$04$iEuHVKo2jBysMLW4PCc3Gux/o0bH5mu1J5Jc3XggCGap9U2hpf/sq");
        return user;
    }

}