package io.smartsoft.rxbroadcast;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.observers.TestObserver;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@RunWith(RobolectricTestRunner.class)
public class OperatorLocalBroadcastRegisterTest {

    @Test
    public void testLocalBroadcast() {
        String action = "TEST_ACTION";
        IntentFilter intentFilter = new IntentFilter(action);
        Application application = RuntimeEnvironment.application;
        Observable<Intent> observable =
            BroadcastObservable.fromLocalBroadcast(application, intentFilter);
        final Observer<Intent> observer = mock(Observer.class);
        final Subscription subscription = observable.subscribe(new TestObserver<>(observer));

        final InOrder inOrder = inOrder(observer);

        inOrder.verify(observer, never()).onNext(any(Intent.class));

        Intent intent = new Intent(action);
        LocalBroadcastManager localBroadcastManager =
            LocalBroadcastManager.getInstance(application);
        localBroadcastManager.sendBroadcast(intent);
        inOrder.verify(observer, times(1)).onNext(intent);

        localBroadcastManager.sendBroadcast(intent);
        inOrder.verify(observer, times(1)).onNext(intent);

        subscription.unsubscribe();
        localBroadcastManager.sendBroadcast(intent);
        inOrder.verify(observer, never()).onNext(any(Intent.class));

        inOrder.verify(observer, never()).onError(any(Throwable.class));
        inOrder.verify(observer, never()).onCompleted();
    }
}