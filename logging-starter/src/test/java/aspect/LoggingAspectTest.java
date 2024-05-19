package aspect;

import lombok.SneakyThrows;
import org.example.aspect.LoggingAspect;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import util.FakeAnnotatedService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {
        LoggingAspect.class,
        AnnotationAwareAspectJAutoProxyCreator.class,
        FakeAnnotatedService.FakeAnnotatedMethodServiceImpl.class,
        FakeAnnotatedService.FakeAnnotatedClassServiceImpl.class
})
public class LoggingAspectTest {

    @Autowired
    @Qualifier("fakeAnnotatedService.FakeAnnotatedMethodServiceImpl")
    private FakeAnnotatedService fakeAnnotatedMethodServiceImpl;

    @Autowired
    @Qualifier("fakeAnnotatedService.FakeAnnotatedClassServiceImpl")
    private FakeAnnotatedService fakeAnnotatedClassService;

    @SpyBean
    private LoggingAspect aspect;

    @Test
    @SneakyThrows
    void logClass() {
        fakeAnnotatedClassService.fakeMethod("Hello");

        verify(aspect).log(any());
    }

    @Test
    @SneakyThrows
    void logMethod() {
        fakeAnnotatedMethodServiceImpl.fakeMethod("Hello");

        verify(aspect).log(any());
    }
}
