package com.project.rad;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class BaseController {
    private final  UserService userService;

    private final PostService postService;

    @Autowired
    public BaseController(UserService userService, PostService postService){
            this.userService = userService;
            this.postService = postService;
    }

    @RequestMapping(value = "/blog/{username)",method = RequestMethod.GET)
    public String baseForUsername(@PathVariable String username,
                                  @RequestParam(defaultValue = "0") int page,
                                  Model model){
        Optional<User> optionalUser = userService.findbyUsername(username);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            Page<Post> posts = postService.findByUserOrderedByDatePageable(user, page);
            Pager pager = new Pager(posts);

            model.addAttribute("pager", pager);
            model.addAttribute("user",user);

            return "/posts";
        }else {
            return "/error";
        }
    }

}
