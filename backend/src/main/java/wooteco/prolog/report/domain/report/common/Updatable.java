package wooteco.prolog.report.domain.report.common;

public interface Updatable<T> {
   void update(T t);
   boolean isSemanticallyEquals(Object o);
   int semanticallyHashcode();
}
