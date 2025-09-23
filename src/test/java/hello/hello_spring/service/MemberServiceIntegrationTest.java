package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
/*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        //then
    }

    @Test
    void findMembers() {
        //given
        Member member1 = new Member();
        member1.setName("member1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("member2");
        memberService.join(member2);

        //when
        List<Member> findMembers = memberService.findMembers();

        //then
        assertThat(findMembers.size()).isEqualTo(2);
        assertThat(findMembers).contains(member1, member2);
    }

    @Test
    void findOne() {
        //given
        Member member1 = new Member();
        member1.setName("member1");
        Long saveId1 = memberService.join(member1);

        Member member2 = new Member();
        member2.setName("member2");
        Long saveId2 = memberService.join(member2);

        //when
        Optional<Member> foundMember = memberService.findOne(saveId1);

        //then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get()).isEqualTo(member1);
    }
}