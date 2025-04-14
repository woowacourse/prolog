package wooteco.prolog.interview.domain;

public interface Interviewer {

    InterviewMessages start(String goal, String question);

    InterviewMessages followUp(InterviewMessages interviewMessages, String answer);

    InterviewMessages finish(InterviewMessages interviewMessages);
}
