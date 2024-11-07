package com.cyshield.Partition.configration;

import com.cyshield.Partition.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }



    @Bean
    public Job largeFileJob() {
        log.info("Start getting large file.");
        return new JobBuilder("largeFileJob", jobRepository)
                .start(chunkStep())
                .build();
    }

    @Bean
    public Step chunkStep() {
        return new StepBuilder("chunkStep", jobRepository)
                .<Customer, Customer>chunk(1000, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<Customer> reader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("largeFileReader")
                .resource(new FileSystemResource("data.csv"))
                .delimited()
                .delimiter(",")
                .names("id", "value", "index")
                .targetType(Customer.class)
                .build();
    }

    @Bean
    public ItemProcessor<Customer, Customer> processor() {
        // TODO replace this with the processor that put this data to kafka.....
        return input -> {
            log.info("xxxx"+ input);

            return Customer.builder()
                .customerId(input.getCustomerId())
                .city(input.getCity())
                .index(input.getIndex())
                .email(input.getEmail())
                .build();
        };
    }

    @Bean
    public FlatFileItemWriter<Customer> writer() {
        return new FlatFileItemWriterBuilder<Customer>()
                .name("outputWriter")
                .resource(new FileSystemResource("output/processed_output.csv"))
                .delimited()
                .delimiter(",")
                .names("id", "squaredValue")
                .build();
    }
}
