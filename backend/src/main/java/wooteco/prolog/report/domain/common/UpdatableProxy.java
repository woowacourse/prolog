package wooteco.prolog.report.domain.common;

public class UpdatableProxy<T extends Updatable<T>> {

    private final T target;

    public UpdatableProxy(T target) {
        this.target = target;
    }

    public void update(UpdatableProxy<T> t) {
        target.update(t.target);
    }

    public T getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdatableProxy<?> that = (UpdatableProxy<?>) o;

        return target.isSemanticallyEquals(that.target);
    }

    @Override
    public int hashCode() {
        return target.semanticallyHashcode();
    }
}
