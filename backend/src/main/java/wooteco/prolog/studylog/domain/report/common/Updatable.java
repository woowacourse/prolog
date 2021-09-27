package wooteco.prolog.studylog.domain.report.common;

public interface Updatable<T> {
   void update(T t);
   boolean isSemanticallyEquals(Object o);
   int semanticallyHashcode();
}
