package wooteco.prolog.member.domain;

import org.springframework.context.ApplicationEvent;

public class MemberCreatedEvent extends ApplicationEvent {

    public MemberCreatedEvent(Member member) {
        super(member);
    }

    public Member getMember() {
        return (Member) getSource();
    }
}
