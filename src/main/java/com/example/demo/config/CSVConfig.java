package com.example.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
 
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.entity.CSVpractice;
import com.example.demo.repository.CSVRepository;

@Configuration
@EnableBatchProcessing
public class CSVConfig {

	@Autowired
	private CSVRepository csvRepository;

 

 

	// READER
	@Bean
	  FlatFileItemReader<CSVpractice> reader() {
		FlatFileItemReader<CSVpractice> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new ClassPathResource("your-file.csv")); // place CSV in src/main/resources
		itemReader.setName("csvReader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}

	// MAPPER
	private LineMapper<CSVpractice> lineMapper() {
		DefaultLineMapper<CSVpractice> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setNames("firstname", "lastname", "college");

		BeanWrapperFieldSetMapper<CSVpractice> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(CSVpractice.class);

		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		return lineMapper;
	}

	// PROCESSOR
	@Bean
	  ItemProcessor<CSVpractice, CSVpractice> processor() {
		return item -> item; // simple pass-through, customize if needed
	}

	// WRITER
	@Bean
	  RepositoryItemWriter<CSVpractice> writer() {
		RepositoryItemWriter<CSVpractice> writer = new RepositoryItemWriter<>();
		writer.setRepository(csvRepository);
		writer.setMethodName("saveAll");
		return writer;
	}

	// STEP
	@Bean
	  Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("csv-step", jobRepository).<CSVpractice, CSVpractice>chunk(10, transactionManager)
				.reader(reader()).processor(processor()).writer(writer()).build();
	}

	// Job
	@Bean
	  Job importCSVJob(JobRepository jobRepository, Step step1) {
		return new JobBuilder("importCSVJob", jobRepository).start(step1).build();
	}
}
