package kr.ac.kopo.yj.bookmarket.controller;

import jakarta.validation.Valid;
import kr.ac.kopo.yj.bookmarket.domain.Member;
import kr.ac.kopo.yj.bookmarket.domain.MemberFormDto;
import kr.ac.kopo.yj.bookmarket.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 회원가입 폼 출력
    @GetMapping(value = "/add")
    public String requestAddMemberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/addMember";
    }

    // 새로운 회원 Entity 추가
    @PostMapping(value = "/add")
    public String submitAddNewMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "member/addMember";
        }

        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/addMember";
        }
        return "redirect:/members";
    }

    //회원 수정 폼
    @GetMapping(value="/update/{memberId}")
    public String requestUpdateMemberForm(@PathVariable(name = "memberId") String memberId, Model model) {
        Member member = memberService.getMemberByMemberId(memberId);
        model.addAttribute("memberFormDto", member);
        return "member/updateMember";
    }

    //회원 수정 기능 수행
    @PostMapping(value="/update")
    public String submitUpdateMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/updateMember";
        }
        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/updateMember";
        }
        return "redirect:/memebers";
    }

    @GetMapping(value = "/delete/{memberId}")
    public String requestDeleteMemberForm(@PathVariable(name = "memberId") String memberId, Model model) {
        memberService.deleteMember(memberId);

        return "redirect:/logout";
    }
}