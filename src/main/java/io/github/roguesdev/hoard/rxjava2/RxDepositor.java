package io.github.roguesdev.hoard.rxjava2;

import io.github.roguesdev.hoard.ReactiveStreamDepositor;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * A {@link ReactiveStreamDepositor} that converts methods to RxJava 2.x
 * equivalents.
 *
 * @param <T> The type the depositor saves and retrieves.
 * @see io.github.roguesdev.hoard.Depositor
 * @since 1.0
 */
public interface RxDepositor<T> {
  Completable store(T value);
  Single<T> retrieve();
  Completable delete();
  Single<Boolean> exists();
}
