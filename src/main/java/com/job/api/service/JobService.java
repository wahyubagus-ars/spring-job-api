package com.job.api.service;

import com.job.api.domain.dto.JobResponseApi;
import com.job.api.exception.AppException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.job.api.constant.AppConstant.ResponseKey.UNKNOWN_ERROR;
import static com.job.api.constant.AppConstant.ResponseMessage.UNKNOWN_ERROR_MSG;

@Service
@Slf4j
public class JobService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url.get_job_list}")
    private String getJobListUrl;

    @Value("${api.url.get_job_by_id}")
    private String getJobByIdUrl;

    @SneakyThrows
    public ResponseEntity<? extends Object> getJobList() {
        try {
            ResponseEntity<JobResponseApi[]> responseEntity = restTemplate.getForEntity(getJobListUrl, JobResponseApi[].class);
            return responseEntity;
        } catch (Exception e) {
            throw new AppException(UNKNOWN_ERROR, UNKNOWN_ERROR_MSG);
        }
    }

    public ResponseEntity<? extends Object> getJobById(String id) {
        try {
            String url = getJobByIdUrl + id;
            ResponseEntity<JobResponseApi> responseEntity = restTemplate.getForEntity(url, JobResponseApi.class);
            return responseEntity;
        } catch (Exception e) {
            throw new AppException(UNKNOWN_ERROR, UNKNOWN_ERROR_MSG);
        }
    }

}
