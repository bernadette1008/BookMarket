package kr.ac.kopo.yj.bookmarket.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.ac.kopo.yj.bookmarket.domain.Member;
import kr.ac.kopo.yj.bookmarket.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/")
    public String welcome(Model model, Authentication authentication, HttpServletRequest request) {
        if(authentication==null)
            return "welcome";

        User user = (User) authentication.getPrincipal();
        String userId = user.getUsername();

        if(userId==null)
            return "redirect:/login";

        Member member = memberService.getMemberByMemberId(userId);

        HttpSession session = request.getSession(true);
        session.setAttribute("member", member);
        return "welcome";
    }
}
