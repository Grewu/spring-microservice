package util;

import org.example.annotation.Logging;

public interface FakeAnnotatedService {
    void fakeMethod(String s);

    class FakeAnnotatedMethodServiceImpl implements FakeAnnotatedService {

        @Override
        @Logging
        public void fakeMethod(String s) {
            //check aspect
        }
    }

    @Logging
    class FakeAnnotatedClassServiceImpl implements FakeAnnotatedService {

        @Override
        public void fakeMethod(String s) {
            //check aspect
        }
    }
}
