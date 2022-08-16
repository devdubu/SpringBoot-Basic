package hello.hellospring.service;


import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//이 코드는 순수한 자바 코드이기에 spring이 이 코드가 특별한지 잘 모른다. 그리고
//autowire를 해도 연결이 안되는 이유가 이 부분이다.

//service라고 하여 spring 컨테이너에 등록해준다.
public class MemberService {

    /*
    *
    * 회원가입
    * */

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    public Long join(Member member){
        //같은 이름이 있는 중복 회원 X
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }); -> 좋은 코드 아님 어쩌피 반환값이 optonal이기 때문에 중복으로 할 이유가 없음

        long start = System.currentTimeMillis();
        try{
            validateDuplicateMember(member);//중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMS = finish - start;
            System.out.println("join = " + timeMS + "ms");
        }

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }
    /*
    * 전체 회원 조회
    * */

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
