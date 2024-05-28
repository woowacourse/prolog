package wooteco.prolog.member.domain;

import org.springframework.context.ApplicationEvent;

public class MemberUpdatedEvent extends ApplicationEvent {
    public MemberUpdatedEvent(Member member) {
        super(member);
    }

    public Member getMember() {
        return (Member) getSource();
    }
}
