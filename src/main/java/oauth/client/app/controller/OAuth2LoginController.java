package oauth.client.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class OAuth2LoginController {

    // @Autowired
    // private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/login/oauth2/code/demo-client")
    public String oauth2Callback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpSession session
    ) {
        System.out.println(code);
        // Kiểm tra state nếu cần
        String storedState = (String) session.getAttribute("oauth2_state");
        if (storedState == null || !storedState.equals(state)) {
            return "redirect:/login?error=invalid_state";
        }

        // // Lấy OAuth2AuthorizedClient từ code
        // OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("demo-client", code);

        // // Xử lý thông tin người dùng và thực hiện các bước tiếp theo
        // // ...

        return "redirect:/home"; // Hoặc trang khác
    }
}
