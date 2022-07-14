# Prolog batch

## 스케쥴링

### 서론

프롤로그 배치는 최소한의 자원을 사용한다. 따라서 스케쥴링을 진행함에 있어 어플리케이션딴의 스케쥴링 기법을 사용할 수 밖에 없다.

이번 절에서는 프롤로그 배치의 스케쥴링 사용법에 대해 알아본다.

### 본론

#### 설정
```java
@EnablePrologBatchSchedule
@EnableBatchProcessing
@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
}
```

프롤로그 배치를 실행하기 위해 기본적으로 배치와 관련된 빈을 주입해주는 `@EnableBatchProcessing` 어노테이션이 필요하며, 
그 다음으로는 스케쥴링을 활성화 시켜주는 `@EnablePrologBatchSchedule` 어노테이션이 필요하다.

#### 적용

스케쥴링 적용은 아래와 같이 적용한다.
```java
@Configuration
public class BatchConfiguration {
    ...
    
    @EnableBatchJob(
        cron = "* * * * * MON-FRI",
        jobParameter = {"name=손너잘", "age=18"}
    )
    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
            ...
    }
}
```

현재는 Configuration을 에서 생성되는 Job 에 대해서만 적용 가능하다.
job을 생성하는 method위에 위와같이 `@EnableBatchJob` 정의하여 사용한다.
cron은 cron expression을 사용하며, jobParameter는 spring batch의 용례를 그대로 따라간다.

#### 주의

이 스케쥴링 방법은, 각각의 job에 대해 서로 다른 스케쥴링을 할 수 있으나, 결국 하나의 어플리케이션에서 동작하도록 설계되어 있으므로 
배포등으로 인해 잠시 배치 어플리케이션이 종료되면 실행중인 배치를 종료시킬 가능성이 있다.
또는 실행 예정인 배치가 동작하지 못할 가능성 또한 존재한다. 
따라서 배치 어플리케이션을 배포할때는 기존 배치들의 플랜을 잘 확인한 후 배포하도록 한다.