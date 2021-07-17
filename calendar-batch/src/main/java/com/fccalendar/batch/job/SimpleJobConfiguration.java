package com.fccalendar.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private static final int chunkSize = 10;

    @Bean
    public Job jdbcCursorItemReaderJob() {
        return jobBuilderFactory.get("jdbcCursorItemReaderJob")
                                .start(jdbcCursorItemReaderStep())
                                .build();
    }

    @Bean
    public Step jdbcCursorItemReaderStep() {
        return stepBuilderFactory.get("jdbcCursorItemReaderStep")
                                 .<SendMailBatchReq, SendMailBatchReq>chunk(chunkSize)
                                 .reader(jdbcCursorItemReader())
                                 .writer(jdbcCursorItemWriter())
                                 .build();
    }

    @Bean
    public JdbcCursorItemReader<SendMailBatchReq> jdbcCursorItemReader() {
        return new JdbcCursorItemReaderBuilder<SendMailBatchReq>()
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(SendMailBatchReq.class))
                .sql("select s.id, s.start_at, s.title, u.email as attendee_email\n" +
                             "from schedules s\n" +
                             "         left join engagements e on s.id = e.schedule_id\n" +
                             "         left join users u on u.id = e.attendee_id\n" +
                             "where date_format(s.start_at, '%Y-%m-%d %H:%i') = date_format" +
                             "(date_add(now(), interval 10 minute), '%Y-%m-%d %H:%i')\n" +
                             "  and e.status = 'ACCEPTED'")
                .name("jdbcCursorItemReader")
                .build();
    }

    private ItemWriter<SendMailBatchReq> jdbcCursorItemWriter() {
        return list -> new RestTemplate().postForObject("http://localhost:8080/api/batch/send/mail",
                                                        list,
                                                        Object.class);
    }
}
