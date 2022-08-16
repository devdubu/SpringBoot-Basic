package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;
    //new 객체를 사용하지 않는 이유
    //membercontroller를 제외하고 다른 모든 컨트롤러들이 member -> 큰 기능이 없기 때문에 여러개를 만들기 보단
    //공용으로 만들어서 공유하는게 좋다

//    @Autowired private MemberService memberService; -> 필드 주입방법(좋은 방법은 아님, 변경할 수 가 없다.)

//    @Autowired
//    public void setMemberService(MemberService memberService){
//        this.memberService = memberService;
//    }

    //public으로 되어잇기 때문에 변경햇을 시에 노출 된다는 단점이 있다, 그러다보니 거의 대부분은 생성자 주입을 권장


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    //Autowired : spring이 멤버서비스와 멤버 컨트롤러와 자동으로 연결 시켜준다
    //생성자를 생성해서 주입 하는 방식임

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);//회원 가입시에 join함수
        return "redirect:/";
    }
    @GetMapping("/members")
    public String List(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
