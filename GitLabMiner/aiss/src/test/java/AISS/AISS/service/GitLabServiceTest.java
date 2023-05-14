package AISS.AISS.service;

import AISS.AISS.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitLabServiceTest {

    @Autowired
    GitLabService service;

    @Test
    void getProjectId() {
        Project project = service.getProjectId("4207231");
        assertTrue(project != null, "the project is empty");
        System.out.println(project);
    }

    @Test
    void getAllCommits() {
    }

    @Test
    void getAllIssues() {
    }

    @Test
    void getIssueComments() {
    }

    @Test
    void groupAllCommits() {
    }

    @Test
    void groupAllIssues() {
    }

    @Test
    void groupIssueComments() {
    }
}