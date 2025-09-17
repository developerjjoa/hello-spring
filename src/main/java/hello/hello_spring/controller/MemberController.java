package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    //private final MemberService memberService = new MemberService();
    //위에 처럼 원래 해왔지만 멤버 서비스는 다른 곳에서도 쓰일 수 있지만, 별 기능이 없기 때문에 여러개를 만들 필요가 없이 하나만 생성해 놓고 공용으로 여러 군데서 쓰면 된다.

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
