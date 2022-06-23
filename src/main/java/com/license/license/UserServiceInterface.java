package com.license.license;

import java.util.List;

public interface UserServiceInterface {
    public List<User> findAllEmailsByURL(String key);
}
