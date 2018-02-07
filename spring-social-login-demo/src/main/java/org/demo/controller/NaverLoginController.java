package org.demo.controller;

import com.github.scribejava.core.model.OAuth2AccessToken;
import javax.servlet.http.HttpSession;
import org.demo.oauth.bo.NaverLoginBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ref ::  https://github.com/Blackseed/NaverLoginTutorial
 *
 * @author zacconding
 * @Date 2018-02-07
 * @GitHub : https://github.com/zacscoding
 */
@Controller
public class NaverLoginController {

    private static final Logger logger = LoggerFactory.getLogger(NaverLoginController.class);

    @Autowired
    private NaverLoginBO naverLoginBO;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String index() {
        logger.info("## request index page");
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
        logger.info("## request login page naver auth url : {}", naverAuthUrl);
        model.addAttribute("url", naverAuthUrl);
        return "login";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state, HttpSession session, Model model) {
        try {
            logger.info("## request callback code : {}, state : {}", code, state);
            /* 네아로 인증이 성공적으로 완료되면 code 파라미터가 전달되며 이를 통해 access token을 발급 */
            OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
            String apiResult = naverLoginBO.getUserProfile(oauthToken);
            model.addAttribute("profile", apiResult);
            return "callback";
        } catch (Exception e) {
            logger.error("## failed to callback", e);
        }

        return "callback";
    }
}
