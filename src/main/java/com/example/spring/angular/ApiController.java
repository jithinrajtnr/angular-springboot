package com.example.spring.angular;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JITHINRAJ on 1/27/2017.
 */
@RestController
@RequestMapping("api/users/")
public class ApiController {

    private static final List<User> users = new ArrayList<>();

    @RequestMapping(value = "all" , method = RequestMethod.GET)
    private List<User> GetAll() {
        System.err.println();
        return users;
    }

    @RequestMapping(value = "GetById/{id}" , method = RequestMethod.GET)
    private User GetById(@PathVariable Integer id) {
        System.err.println();
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @RequestMapping(value = "GetByUsername/{username}" , method = RequestMethod.GET)
    private User GetByUsername(@PathVariable String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    @RequestMapping(value = "Authenticate" , method = RequestMethod.POST)
    private ApiResponse Create(@RequestBody Credentials credentials) {
        if (users.stream().filter(user -> user.getUsername().equals(credentials.getUsername()) &&
                user.getPassword().equals(credentials.getPassword())).findFirst().orElse(null) == null){
            return new ApiResponse(false , "User not found");
        }
        else {
            return new ApiResponse(true , "Success");
        }
    }


    @RequestMapping(value = "Create" , method = RequestMethod.POST)
    private ApiResponse Create(@RequestBody User user) {
        int newId = users.stream().sorted((f1 , f2) -> f2.getId() - f1.getId()).findFirst().orElse(getFirstUser()).getId();
        user.setId(++newId);
        users.add(user);
        return new ApiResponse(true , user.getUsername());
    }

    @RequestMapping(value = "Update" , method = RequestMethod.PUT)
    private ApiResponse Update(@PathVariable Integer id ,  @RequestBody User user) {
        User user11 = users.stream().filter(user1 -> user1.getId() == id).findAny().orElse(null);
        return new ApiResponse(true , user.getUsername());
    }

    @RequestMapping(value = "Delete" ,method = RequestMethod.DELETE)
    private ApiResponse Delete(@PathVariable Integer id) {
        User user11 = users.stream().filter(user1 -> user1.getId() == id).findAny().orElse(null);
        return new ApiResponse(true , "");
    }

    private User getFirstUser() {
        User user = new User();
        user.setId(1);
        return user;
    }
}
