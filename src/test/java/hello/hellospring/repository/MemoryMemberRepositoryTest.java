package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();
    //테스트는 순서에 상관없이 무작위로 진행하기 때문에 이전 캐쉬로 인해서 오류가 생길 수도 있다.
    //그렇기 때문에 이전 테스트가 끝난다면, 캐시를 지워주는 역할을 해야한다.
    //테스트는 서로의 의존 관계가 없이 끝나야함
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }//어떠한 메서드가 끝난 다음에 바로 실행되는 콜백 메서드이다.


    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
//        Assertions.assertEquals(member, result);
        //많이 쓰이는 방법
        assertThat(member).isEqualTo(result);
    }
    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }
    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }
}
