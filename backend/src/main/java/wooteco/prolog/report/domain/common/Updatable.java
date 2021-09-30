package wooteco.prolog.report.domain.common;

public interface Updatable<T> {
   void update(T t);
   boolean isSemanticallyEquals(Object o);
   int semanticallyHashcode();
}
